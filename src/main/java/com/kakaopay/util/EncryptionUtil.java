package com.kakaopay.util;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EncryptionUtil {
    public static final String CARD_INFO_DELIMITER = "|";

    public static String of(String cardNumber, String validPeriod, String cvc) {
        return of(Arrays.asList(cardNumber, validPeriod, cvc));
    }

    private static String of(List<String> cardInfo) {
        if (CollectionUtils.isEmpty(cardInfo)) {
            return "";
        }

        return cardInfo
            .stream()
            .collect(Collectors.joining(CARD_INFO_DELIMITER));
    }
}
