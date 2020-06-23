package com.kakaopay.assignment.controller.dto;

import com.kakaopay.assignment.controller.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PayRequestDtoTest extends BaseTest {
    private static final String URL = "/pay";

    private PayRequestDto payRequestDto;

    @BeforeEach
    void setUp() {
        payRequestDto = PayRequestDto.builder()
            .cardNo("9999999999")
            .installmentMonths(12)
            .validPeriod("0725")
            .cvc("293")
            .paymentAmount(1000000000)
            .build();
    }

    @DisplayName("잘못된 카드번호 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(strings = {
        "99999999.9",
        "-123456789",
        "문자열카드번호문자열카드번호",
        "abcdefghijklmn",
        "9",
        "999999999",
        "00000000000000000",
    })
    @NullAndEmptySource
    void test_invalidCardNos(String cardNo) throws Exception {
        payRequestDto.setCardNo(cardNo);

        assertPostResult(URL, payRequestDto, status().isBadRequest());
    }

    @DisplayName("정상적인 카드번호 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(strings = {
        "0000000000",
        "9999999999999999",
    })
    void test_CardNos(String cardNo) throws Exception {
        payRequestDto.setCardNo(cardNo);

        assertPostResult(URL, payRequestDto, status().isOk());
    }

    @DisplayName("비정상적인 유효기간 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(strings = {
        "12.1",
        "00000",
        "0099",
        "-010",
        "1300",
        "129",
        "010",
        "abcd",
        "한글변수",
    })
    @NullAndEmptySource
    void test_invalidValidPeriod(String validPeriod) throws Exception {
        payRequestDto.setValidPeriod(validPeriod);

        assertPostResult(URL, payRequestDto, status().isBadRequest());
    }

    @DisplayName("정상적인 유효기간 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(strings = {
        "0100",
        "0199",
        "0650",
        "1200",
        "1299",
    })
    void test_validPeriod(String validPeriod) throws Exception {
        payRequestDto.setValidPeriod(validPeriod);

        assertPostResult(URL, payRequestDto, status().isOk());
    }


    @DisplayName("비정상적인 cvc 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(strings = {
        "0.0",
        "-01",
        "-99",
        "99",
        "0000",
        "abc",
        "한글임",
    })
    @NullAndEmptySource
    void test_invalidCvc(String cvc) throws Exception {
        payRequestDto.setCvc(cvc);

        assertPostResult(URL, payRequestDto, status().isBadRequest());
    }

    @DisplayName("정상적인 cvc 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(strings = {
        "000",
        "999",
    })
    void test_validCvc(String cvc) throws Exception {
        payRequestDto.setCvc(cvc);

        assertPostResult(URL, payRequestDto, status().isOk());
    }

    @DisplayName("비정상적인 할부개월 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(ints = {
        -1,
        13,
        99,
    })
    @NullSource
    void test_invalidInstallmentMonths(Integer installmentMonths) throws Exception {
        payRequestDto.setInstallmentMonths(installmentMonths);

        assertPostResult(URL, payRequestDto, status().isBadRequest());
    }

    @DisplayName("정상적인 할부개월 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(ints = {
        0,
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
    })
    void test_InstallmentMonths(int installmentMonths) throws Exception {
        payRequestDto.setInstallmentMonths(installmentMonths);

        assertPostResult(URL, payRequestDto, status().isOk());
    }

    @DisplayName("비정상적인 결제금액 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(ints = {
        -100,
        0,
        99,
        1000000001,
        2099999999
    })
    @NullSource
    void test_invalidPaymentAmount(Integer paymentAmount) throws Exception {
        payRequestDto.setPaymentAmount(paymentAmount);

        assertPostResult(URL, payRequestDto, status().isBadRequest());
    }

    @DisplayName("정상적인 결제금액 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(ints = {
        100,
        50000,
        999999999,
        1000000000,
    })
    void test_paymentAmount(int paymentAmount) throws Exception {
        payRequestDto.setPaymentAmount(paymentAmount);

        assertPostResult(URL, payRequestDto, status().isOk());
    }

    @DisplayName("비정상적인 vat 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(ints = {
        -1,
        -5000,
        1000000001,
        2099999999,
    })
    void test_invalidVat(int vat) throws Exception {
        payRequestDto.setVat(vat);

        assertPostResult(URL, payRequestDto, status().isBadRequest());
    }

    @DisplayName("정상적인 vat 테스트")
    @ParameterizedTest(name = "{index} {displayName} for {0}")
    @ValueSource(ints = {
        0,
        1,
        100,
        50000,
        999999999,
        1000000000,
    })
    @NullSource
    void test_Vat(Integer vat) throws Exception {
        payRequestDto.setVat(vat);

        assertPostResult(URL, payRequestDto, status().isOk());
    }
}