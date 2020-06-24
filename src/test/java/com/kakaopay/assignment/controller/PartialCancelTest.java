package com.kakaopay.assignment.controller;

import com.kakaopay.assignment.controller.dto.CancelRequestDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.controller.dto.PayResponseDto;
import com.kakaopay.assignment.controller.dto.VatCalculable;
import com.kakaopay.assignment.service.PaymentHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Slf4j
class PartialCancelTest extends BaseTest implements VatCalculable {
    private static final String URL = "/cancel";
    @Autowired
    PaymentHistoryService service;
    private PayRequestDto payRequestDto;
    private PayResponseDto payResponseDto;
    private CancelRequestDto cancelRequestDto;

    @Test
    @DisplayName("부분 취소 테스트 1")
    void cancelPartially1() throws Exception {
        payRequestDto = PayRequestDto.builder()
            .cardNo("1234123412")
            .installmentMonths(0)
            .validPeriod("0620")
            .cvc("999")
            .paymentAmount(11000)
            .vat(1000)
            .build();

        payResponseDto = service.insert(payRequestDto);

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(payResponseDto.getMgmtNo())
            .cancelAmount(1100)
            .vat(100)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isOk());

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(cancelRequestDto.getMgmtNo())
            .cancelAmount(3300)
            .vat(null)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isOk());


        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(cancelRequestDto.getMgmtNo())
            .cancelAmount(7000)
            .vat(null)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isBadRequest());

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(cancelRequestDto.getMgmtNo())
            .cancelAmount(6600)
            .vat(700)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isBadRequest());

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(cancelRequestDto.getMgmtNo())
            .cancelAmount(6600)
            .vat(600)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isOk());

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(cancelRequestDto.getMgmtNo())
            .cancelAmount(100)
            .vat(null)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isBadRequest());
    }

    @Test
    @DisplayName("부분 취소 테스트 2")
    void cancelPartially2() throws Exception {
        payRequestDto = PayRequestDto.builder()
            .cardNo("1234123412")
            .installmentMonths(0)
            .validPeriod("0620")
            .cvc("999")
            .paymentAmount(20000)
            .vat(909)
            .build();

        payResponseDto = service.insert(payRequestDto);

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(payResponseDto.getMgmtNo())
            .cancelAmount(10000)
            .vat(0)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isOk());

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(payResponseDto.getMgmtNo())
            .cancelAmount(10000)
            .vat(0)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isBadRequest());


        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(payResponseDto.getMgmtNo())
            .cancelAmount(10000)
            .vat(909)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isOk());
    }

    @Test
    @DisplayName("부분 취소 테스트 3")
    void cancelPartially3() throws Exception {
        payRequestDto = PayRequestDto.builder()
            .cardNo("1234123412")
            .installmentMonths(0)
            .validPeriod("0620")
            .cvc("999")
            .paymentAmount(20000)
            .vat(null)
            .build();

        payResponseDto = service.insert(payRequestDto);

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(payResponseDto.getMgmtNo())
            .cancelAmount(10000)
            .vat(1000)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isOk());

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(payResponseDto.getMgmtNo())
            .cancelAmount(10000)
            .vat(909)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isBadRequest());


        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(payResponseDto.getMgmtNo())
            .cancelAmount(10000)
            .vat(null)
            .build();

        assertPostResult(URL, cancelRequestDto, status().isOk());
    }
}