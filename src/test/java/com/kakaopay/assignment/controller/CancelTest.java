package com.kakaopay.assignment.controller;

import com.kakaopay.assignment.controller.dto.CancelRequestDto;
import com.kakaopay.assignment.controller.dto.FoundPaymentDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.controller.dto.PayResponseDto;
import com.kakaopay.assignment.service.PaymentHistoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class CancelTest extends BaseTest {
    private static final String URL = "/cancel";

    private PayRequestDto payRequestDto;

    private CancelRequestDto cancelRequestDto;

    @Autowired
    private PaymentHistoryService service;

    @BeforeEach
    void setUp() {
        payRequestDto = PayRequestDto.builder()
            .cardNo("1234123412")
            .installmentMonths(0)
            .validPeriod("0620")
            .cvc("999")
            .paymentAmount(11000)
            .vat(1000)
            .build();

        PayResponseDto payResponseDto = service.insert(payRequestDto);

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(payResponseDto.getMgmtNo())
            .cancelAmount(1100)
            .build();
    }

    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @CsvSource({
        "11000, -1",
        "5000, 1000"
    })
    @DisplayName("결제 취소 테스트")
    void cancel(Integer cancelAmount, Integer cancelVat) throws Exception {
        if (cancelVat == -1) {
            cancelVat = null;
        }

        cancelRequestDto.setCancelAmount(cancelAmount);
        cancelRequestDto.setVat(cancelVat);

        assertPostResult(URL, cancelRequestDto, status().isOk());
    }

    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @CsvSource({
        "11001, 0",
        "5000, 1001"
    })
    @DisplayName("결제 취소 실패 테스트")
    void cancel_forInvalidAmountAndVat(Integer cancelAmount, Integer cancelVat) throws Exception {
        cancelRequestDto.setCancelAmount(cancelAmount);
        cancelRequestDto.setVat(cancelVat);

        assertPostResult(URL, cancelRequestDto, status().isBadRequest());
    }
}