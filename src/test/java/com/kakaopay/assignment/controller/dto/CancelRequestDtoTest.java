package com.kakaopay.assignment.controller.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.assignment.service.PaymentHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CancelRequestDtoTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentHistoryService service;

    private PayRequestDto payRequestDto;
    private CancelRequestDto cancelRequestDto;

    @BeforeEach
    void setUp() {
        payRequestDto = PayRequestDto.builder()
            .cardNo("9999999999")
            .installmentMonths(12)
            .validPeriod("0725")
            .cvc("293")
            .paymentAmount(1000000000)
            .build();

        PayResponseDto payResponseDto = service.insert(payRequestDto);

        cancelRequestDto = CancelRequestDto.builder()
            .mgmtNo(payResponseDto.getMgmtNo())
            .cancelAmount(1000000000)
            .build();
    }

    @DisplayName("비정상적인 관리번호 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(strings = {
        "abcdefghij123456789",
        "1111111111111111111",
        "aaaaaaaaaaaaaaaaaaa",
    })
    @NullAndEmptySource
    void test_invalidMgmtNo(String mgmtNo) throws Exception {
        cancelRequestDto.setMgmtNo(mgmtNo);

        assertResult(status().isBadRequest());
    }

    @DisplayName("정상적인 관리번호 테스트")
    @Test
    void test_paymentAmount() throws Exception {
        assertResult(status().isOk());
    }

    @DisplayName("비정상적인 취소금액 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(ints = {
        -100,
        -1,
        0,
        1000000001,
        2099999999
    })
    @NullSource
    void test_invalidCancelAmount(Integer cancelAmount) throws Exception {
        cancelRequestDto.setCancelAmount(cancelAmount);

        assertResult(status().isBadRequest());
    }

    @DisplayName("정상적인 취소금액 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(ints = {
        1,
        9,
        10,
        99,
        100,
        50000,
        999999999,
        1000000000,
    })
    void test_paymentAmount(Integer cancelAmount) throws Exception {
        cancelRequestDto.setCancelAmount(cancelAmount);

        assertResult(status().isOk());
    }

    @DisplayName("비정상적인 vat 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(ints = {
        -1,
        -5000,
        -100,
        1000000001,
        2099999999,
    })
    void test_invalidVat(Integer vat) throws Exception {
        cancelRequestDto.setVat(vat);

        assertResult(status().isBadRequest());
    }

    @DisplayName("정상적인 vat 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(ints = {
        0,
        1,
        100,
        50000,
        90909088,
    })
    void test_Vat(Integer vat) throws Exception {
        cancelRequestDto.setVat(vat);

        assertResult(status().isOk());
    }

    private void assertResult(ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(post("/cancel")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(cancelRequestDto)))
            .andDo(print())
            .andExpect(resultMatcher);
    }
}