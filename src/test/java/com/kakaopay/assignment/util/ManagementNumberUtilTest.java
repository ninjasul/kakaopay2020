package com.kakaopay.assignment.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ManagementNumberUtilTest {

    @Test
    void generateManagementNumber() {
        for (int i = 0; i < 100; i++) {
            String managementNumber = ManagementNumberUtil.generate();

            assertThat(managementNumber).isNotEmpty();
            assertThat(managementNumber.length()).isEqualTo(20);

            log.debug("{}", managementNumber);
        }
    }
}