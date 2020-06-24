package com.kakaopay.assignment.controller;

import com.kakaopay.assignment.controller.dto.FoundPaymentDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.controller.dto.PayResponseDto;
import com.kakaopay.assignment.repository.PaymentHistoryRepository;
import com.kakaopay.assignment.service.PaymentHistoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class PayTest extends BaseTest {
    private static final String URL = "/pay";

    private PayRequestDto payRequestDto;

    @Autowired
    private PaymentHistoryService service;

    @Autowired
    private PaymentHistoryRepository repository;

    @BeforeEach
    void setUp() {
        payRequestDto = PayRequestDto.builder()
            .cardNo("1234123412")
            .installmentMonths(0)
            .validPeriod("0620")
            .cvc("999")
            .paymentAmount(50000000)
            .build();
    }

    @Test
    @DisplayName("결제 내역 insert 테스트")
    void insert() throws Exception {
        ResultActions resultActions = assertPostResult(URL, payRequestDto, status().isOk());
        resultActions.andExpect(jsonPath("mgmtNo").exists());
        resultActions.andExpect(jsonPath("mgmtNo").value(Matchers.hasLength(20)));
        resultActions.andExpect(jsonPath("paymentData").exists());
        resultActions.andExpect(jsonPath("paymentData").value(Matchers.hasLength(450)));

        PayResponseDto response = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), PayResponseDto.class);

        FoundPaymentDto foundPaymentDto = service.findByMgmtNo(response.getMgmtNo());

        assertThat(foundPaymentDto).isNotNull();
        assertThat(foundPaymentDto.getCardInfo().getCardNo()).isEqualTo(payRequestDto.getCardNo());
        assertThat(foundPaymentDto.getCardInfo().getValidPeriod()).isEqualTo(payRequestDto.getValidPeriod());
        assertThat(foundPaymentDto.getCardInfo().getCvc()).isEqualTo(payRequestDto.getCvc());
        assertThat(foundPaymentDto.getAmountInfo().getPaidAmount()).isEqualTo(payRequestDto.getPaymentAmount());
        assertThat(foundPaymentDto.getAmountInfo().getPaidVat()).isEqualTo(payRequestDto.getVat());
    }
}