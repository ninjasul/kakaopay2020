package com.kakaopay.assignment.service;

import com.kakaopay.assignment.controller.dto.CancelRequestDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.controller.dto.FoundPaymentDto;
import com.kakaopay.assignment.controller.dto.PayResponseDto;
import com.kakaopay.assignment.domain.Body;
import com.kakaopay.assignment.domain.Header;
import com.kakaopay.assignment.domain.field.RequestType;
import com.kakaopay.assignment.entity.PaymentHistory;
import com.kakaopay.assignment.repository.PaymentHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    @Autowired
    private PaymentHistoryRepository repository;

    @Override
    @Transactional
    public PayResponseDto insert(PayRequestDto requestDto) {
        Body body = Body.from(requestDto);
        Header header = Header.from(RequestType.PAYMENT);
        header.length(header.getLength() + body.getLength());

        PaymentHistory paymentHistory = PaymentHistory.from(body, header);
        repository.save(paymentHistory);

        return PayResponseDto.from(paymentHistory);
    }

    @Override
    @Transactional
    public PayResponseDto cancel(CancelRequestDto cancelRequestDto) {
        PaymentHistory paymentHistory =
            Optional.ofNullable(repository.findByMgmtNo(cancelRequestDto.getMgmtNo()))
                .orElseThrow(EntityNotFoundException::new);

        if (paymentHistory.isCancelled()) {
            throw new IllegalStateException("해당 결제는 이미 취소되었습니다.");
        }

        if (!paymentHistory.canCancel(cancelRequestDto)) {
            throw new IllegalArgumentException("잘못된 취소 요청입니다.");
        }

        paymentHistory.cancel(cancelRequestDto);
        return PayResponseDto.from(paymentHistory);
    }

    @Override
    public FoundPaymentDto findByMgmtNo(String mgmtNo) {
        return Optional.ofNullable(repository.findByMgmtNo(mgmtNo))
            .map(FoundPaymentDto::from)
            .orElseThrow(EntityNotFoundException::new);
    }
}