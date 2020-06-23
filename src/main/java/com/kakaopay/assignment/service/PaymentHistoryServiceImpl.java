package com.kakaopay.assignment.service;

import com.kakaopay.assignment.controller.dto.CancelRequestDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.controller.dto.FoundPaymentDto;
import com.kakaopay.assignment.controller.dto.PayResponseDto;
import com.kakaopay.assignment.domain.Protocol;
import com.kakaopay.assignment.domain.field.RequestType;
import com.kakaopay.assignment.entity.PaymentHistory;
import com.kakaopay.assignment.repository.PaymentHistoryRepository;
import com.kakaopay.assignment.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    @Autowired
    private PaymentHistoryRepository repository;

    @Override
    @Transactional
    public PayResponseDto insert(PayRequestDto requestDto) {
        Protocol protocol = Protocol.from(requestDto);
        PaymentHistory paymentHistory = PaymentHistory.from(protocol, RequestType.PAYMENT);
        repository.save(paymentHistory);

        return PayResponseDto.from(paymentHistory);
    }

    @Override
    @Transactional
    public PayResponseDto cancel(CancelRequestDto cancelRequestDto) throws Exception {
        PaymentHistory paymentHistory =
            Optional.ofNullable(repository.findCancellableByMgmtNo(cancelRequestDto.getMgmtNo()))
                .orElseThrow(EntityNotFoundException::new);

        log.info("found paymentHistory: {}", StringUtils.toPrettyJson(paymentHistory));

        if (paymentHistory.isCancelled()) {
            log.info("isCancelled: true");
            throw new IllegalStateException("해당 결제는 이미 취소되었습니다.");
        }

        if (Objects.isNull(cancelRequestDto.getVat())) {
            setVat(cancelRequestDto, paymentHistory);
        }

        if (!paymentHistory.canCancel(cancelRequestDto)) {
            log.info("canCancel: false");
            throw new IllegalArgumentException("잘못된 취소 요청입니다.");
        }

        paymentHistory.cancel(cancelRequestDto);

        Protocol cancelProtocol = Protocol.from(cancelRequestDto, paymentHistory);
        PaymentHistory cancelHistory = PaymentHistory.from(cancelProtocol, RequestType.CANCEL);
        repository.save(cancelHistory);

        return PayResponseDto.from(cancelHistory);
    }

    private void setVat(CancelRequestDto cancelRequestDto, PaymentHistory paymentHistory) {
        cancelRequestDto.setVat(
            Math.min(
                paymentHistory.getRemainingVat(),
                cancelRequestDto.getCalculatedVat(cancelRequestDto.getCancelAmount(), null)
            )
        );
    }

    @Override
    public FoundPaymentDto findByMgmtNo(String mgmtNo) {
        return Optional.ofNullable(repository.findByMgmtNo(mgmtNo))
            .map(FoundPaymentDto::from)
            .orElseThrow(EntityNotFoundException::new);
    }
}