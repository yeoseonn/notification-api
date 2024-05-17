package com.nfs.front.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class IdGenerator {
    // left shift amounts
    private static final int TIMESTAMP_SHIFT = 10;

    // exclusive
    private static final int MAX_RANDOM = 0x800000;


    public static long generate() {
        long time = System.currentTimeMillis();
        return (time << TIMESTAMP_SHIFT) + nextInt();
    }

    private static int nextInt() {
        return newSecureRandom().nextInt(MAX_RANDOM);
    }

    private static SecureRandom newSecureRandom() {
        SecureRandom random;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException cause) {
            random = new SecureRandom();
        }
        return random;
    }
}

