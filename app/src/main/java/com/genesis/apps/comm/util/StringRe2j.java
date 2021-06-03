package com.genesis.apps.comm.util;

import com.google.re2j.Pattern;

public class StringRe2j {

    public static final String PHONE_ORIGINAL = "^(\\d{3})-?(\\d{3,4})-?(\\d{4})$";
    public static final String PHONE_MASK = "$1-****-$3";

    private StringRe2j() {
        throw new IllegalStateException("Utility class");
    }

    public static String replaceFirst(String input, String regex, String replacement) {
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    public static String replaceAll(String input, String regex, String replacement) {
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }

    public static boolean matches(String input, String regex) {
        return Pattern.matches(regex, input);
    }
}
