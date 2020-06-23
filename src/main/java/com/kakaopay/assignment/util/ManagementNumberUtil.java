package com.kakaopay.assignment.util;

import java.security.SecureRandom;
import java.util.Base64;

public class ManagementNumberUtil {
    public static String generate() {
        byte[] seed = String.valueOf(System.currentTimeMillis()).getBytes();
        SecureRandom random = new SecureRandom(seed);

        byte bytes[] = new byte[15];
        random.nextBytes(bytes);

        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
