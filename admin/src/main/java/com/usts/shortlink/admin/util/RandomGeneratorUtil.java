package com.usts.shortlink.admin.util;

import java.security.SecureRandom;

/**
 * 分组id随机生成器
 */
public final class RandomGeneratorUtil {

    private static final String BASE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int DEFAULT_LENGTH = 6;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private RandomGeneratorUtil() {
    }

    /**
     * 生成六位数字和字母组成的随机字符串
     *
     * @return 六位随机字符串
     */
    public static String generateRandom() {
        StringBuilder result = new StringBuilder(DEFAULT_LENGTH);
        for (int i = 0; i < DEFAULT_LENGTH; i++) {
            int index = SECURE_RANDOM.nextInt(BASE.length());
            result.append(BASE.charAt(index));
        }
        return result.toString();
    }
}
