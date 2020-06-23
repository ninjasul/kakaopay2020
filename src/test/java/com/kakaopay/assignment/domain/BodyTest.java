package com.kakaopay.assignment.domain;

import com.kakaopay.assignment.util.ManagementNumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class BodyTest {

    private Body body;

    @BeforeEach
    void setUp() {
        body = Body.builder()
            .cardNo("1234123412341234")
            .installmentMonths(0)
            .validPeriod("1299")
            .cvc("000")
            .amount(1000000000)
            .vat(1000)
            .orgMgmtNo(ManagementNumberUtil.generate())
            .build();
    }

    @Test
    void getLength() {
        int length = body.getLength();

        assertThat(length).isEqualTo(getBodyLength(body));

        log.debug("length: {}", length);
    }

    private int getBodyLength(Body body) {
        return body.getCardNo().length() +
            String.valueOf(body.getInstallmentMonths()).length() +
            body.getValidPeriod().length() +
            body.getCvc().length() +
            String.valueOf(body.getAmount()).length() +
            String.valueOf(body.getVat()).length() +
            body.getOrgMgmtNo().length() +
            body.getEncryptedCardInfo().length();
    }
}