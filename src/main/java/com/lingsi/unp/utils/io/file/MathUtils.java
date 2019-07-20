package com.lingsi.unp.utils.io.file;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author xy@lingsi
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MathUtils {

    public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
        if (d1 == null) {
            return d2;
        }
        if (d2 == null) {
            return d1;
        }
        return d1.add(d2);
    }

    private static final String VALUE_SOURCES = "0123456789";

    /**
     * 生成length位数字验证码
     *
     * @param length 长度
     * @return 验证码
     */
    public static String randomCaptcha(int length) {
        Random rand = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            captcha.append(VALUE_SOURCES.charAt(rand.nextInt(VALUE_SOURCES.length() - 1)));
        }
        return captcha.toString();
    }

}
