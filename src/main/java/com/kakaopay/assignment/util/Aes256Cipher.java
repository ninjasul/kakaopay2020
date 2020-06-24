package com.kakaopay.assignment.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
public class Aes256Cipher {
    public static final String CARD_INFO_DELIMITER = "|";
    public static final String CARD_INFO_SPLITTER = "\\|";
    private static final String SECRET_KEY = "kakaopaykakaopay1234123456789098";
    private static final String IV = SECRET_KEY.substring(0, 16);

    public static String encryptCardInfo(String... values) {
        if (ArrayUtils.isEmpty(values)) {
            return "";
        }

        return encrypt(Arrays.asList(values));
    }

    private static String encrypt(List<String> cardInfo) {
        return encrypt(getOriginalString(cardInfo));
    }

    private static String getOriginalString(List<String> cardInfo) {
        return cardInfo
            .stream()
            .collect(Collectors.joining(CARD_INFO_DELIMITER));
    }

    public static String encrypt(String str) {
        byte[] keyData = SECRET_KEY.getBytes();

        SecretKey secureKey = new SecretKeySpec(keyData, "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));

            byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encodeBase64(encrypted));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }
    }

    public static List<String> decryptCardInfo(String str) {
        return Arrays.stream(decrypt(str).split(CARD_INFO_SPLITTER))
            .collect(toList());
    }

    public static String decrypt(String str) {
        byte[] keyData = SECRET_KEY.getBytes();
        SecretKey secureKey = new SecretKeySpec(keyData, "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));

            byte[] byteStr = Base64.decodeBase64(str.getBytes());

            return new String(cipher.doFinal(byteStr), StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }
    }
}
