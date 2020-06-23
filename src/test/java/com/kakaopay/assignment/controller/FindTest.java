package com.kakaopay.assignment.controller;

import com.kakaopay.assignment.controller.dto.FoundPaymentDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.controller.dto.PayResponseDto;
import com.kakaopay.assignment.service.PaymentHistoryService;
import com.kakaopay.assignment.util.ManagementNumberUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class FindTest extends BaseTest {
    private static final String URL = "/find/";

    private PayRequestDto payRequestDto;

    private String mgmtNo;

    @Autowired
    private PaymentHistoryService service;

    @BeforeEach
    void setUp() {
        payRequestDto = PayRequestDto.builder()
            .cardNo("1234123412")
            .installmentMonths(0)
            .validPeriod("0620")
            .cvc("999")
            .paymentAmount(50000000)
            .build();

        PayResponseDto payResponseDto = service.insert(payRequestDto);
        mgmtNo = payResponseDto.getMgmtNo();
    }

    @Test
    void find() throws Exception {
        ResultActions resultActions = assertGetResult(URL + mgmtNo, status().isOk());
        FoundPaymentDto foundPaymentDto = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), FoundPaymentDto.class);

        assertThat(foundPaymentDto).isNotNull();
        assertThat(foundPaymentDto.getCardInfo().getCardNo()).isEqualTo(payRequestDto.getCardNo());
        assertThat(foundPaymentDto.getCardInfo().getValidPeriod()).isEqualTo(payRequestDto.getValidPeriod());
        assertThat(foundPaymentDto.getCardInfo().getCvc()).isEqualTo(payRequestDto.getCvc());
        assertThat(foundPaymentDto.getAmountInfo().getPaidAmount()).isEqualTo(payRequestDto.getPaymentAmount());
        assertThat(foundPaymentDto.getAmountInfo().getPaidVat()).isEqualTo(payRequestDto.getVat());
    }

    @Test
    void find_forEntityNotFoundException() throws Exception {
        assertGetResult(URL + ManagementNumberUtil.generate(), status().isBadRequest());
    }
}