package com.genesis.apps.comm.util;

import com.google.re2j.Pattern;

public class StringRe2j {

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
