package com.kakaopay.assignment.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Aes256CipherTest {
    private String cardNo = "1234567890123456";
    private String validPeriod = "0620";
    private String cvc = "999";

    @Test
    @DisplayName("카드 정보 암호화 테스트")
    void encrypt() {
        String encryptedStr = Aes256Cipher.encryptCardInfo(cardNo, validPeriod, cvc);

        log.debug("encryptedStr: {}", encryptedStr);

        List<String> decryptedStrs = Aes256Cipher.decryptCardInfo(encryptedStr);

        log.debug("decryptedStrs: {}", decryptedStrs);

        assertThat(decryptedStrs).containsExactly(cardNo, validPeriod, cvc);
    }
}