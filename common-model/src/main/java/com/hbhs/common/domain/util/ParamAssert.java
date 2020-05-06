package com.hbhs.common.domain.util;

import java.util.Collection;

public class ParamAssert {
    private ParamAssert() {
    }

    public static boolean isNull(Object arg0) {
        return null == arg0;
    }

    public static void assertNull(Object arg0, String message) {
        expectTrue(isNull(arg0), message);
    }

    public static boolean isNotNull(Object arg0) {
        return !isNull(arg0);
    }

    public static void assertNotNull(Object arg0, String message) {
        expectTrue(isNotNull(arg0), message);
    }

    public static boolean isBlank(String arg0) {
        return arg0 == null || "".equalsIgnoreCase(arg0.trim());
    }

    public static void assertBlank(String arg0, String message) {
        expectTrue(isBlank(arg0), message);
    }

    public static boolean isNotBlank(String arg0) {
        return !isBlank(arg0);
    }

    public static void assertNotBlank(String arg0, String message) {
        expectTrue(isNotBlank(arg0), message);
    }

    public static boolean isNotEmpty(Collection coll) {
        return coll == null || coll.size() == 0;
    }

    public static void assertNotEmpty(Collection coll, String message) {
        expectTrue(isNotEmpty(coll), message);
    }

    public static void expectTrue(boolean result, String message) {
        if (!result) {
            throw new RuntimeException(message);
        }
    }
}
