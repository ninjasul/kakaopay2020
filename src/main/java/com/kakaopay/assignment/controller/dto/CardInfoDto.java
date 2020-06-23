package com.kakaopay.assignment.controller.dto;

import com.kakaopay.assignment.util.Aes256Cipher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CardInfoDto {
    private String cardNo;
    private String validPeriod;
    private String cvc;

    public static CardInfoDto of(String cardNo, String validPeriod, String cvc) {
        return CardInfoDto.builder()
            .cardNo(cardNo)
            .validPeriod(validPeriod)
            .cvc(cvc)
            .build();
    }

    @Builder
    public CardInfoDto(String cardNo, String validPeriod, String cvc) {
        this.cardNo = cardNo;
        this.validPeriod = validPeriod;
        this.cvc = cvc;
    }

    public String toEncryptedString() {
        return Aes256Cipher.encryptCardInfo(cardNo, validPeriod, cvc);
    }
}
