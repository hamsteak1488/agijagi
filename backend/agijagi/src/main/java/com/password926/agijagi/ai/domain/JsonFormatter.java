package com.password926.agijagi.ai.domain;

public class JsonFormatter {

    public static String format(String string) {
        if (string.startsWith("`")) {
            return string.substring(7, string.length() - 3);
        }
        return string;
    }
}
