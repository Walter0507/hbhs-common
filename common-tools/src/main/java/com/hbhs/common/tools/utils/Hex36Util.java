package com.hbhs.common.tools.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * 62进制转换工具，该工具类支持36进制到10进制的转换
 * <BR>62进制由字符[0-9a-z]组成，分别代表值0-35
 * <br>
 **/
@Slf4j
public class Hex36Util {

    /**
     * 将10进制转化为62进制
     *
     * @param number 数字
     * @return 62进制
     */
    public static String hex10To36(long number) {
        return hex10To36(number, 0);
    }

    /**
     * 将10进制转化为62进制
     *
     * @param number 数字
     * @param length 转化成的62进制长度，不足length长度的话高位补0，否则不改变什么
     * @return 62进制
     */
    public static String hex10To36(long number, int length) {
        long rest = number;
        // 使用栈实现
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(int2Char((int) (rest % 36)));
            rest = rest / 36;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        // 高位补0
        int resultLength = result.length();
        StringBuilder temp0 = new StringBuilder();
        for (int i = 0; i < length - resultLength; i++) {
            temp0.append('0');
        }

        return temp0.toString() + result.toString();
    }

    public static long hex36To10(String hex36) {
        if (hex36 == null || "".equals(hex36.trim())) {
            return 0;
        }
        long result = 0L;
        char[] charArray = hex36.toCharArray();
        //系数
        long ratio = 1L;
        int index = charArray.length - 1;
        while (index >= 0) {
            int intValue = char2Int(charArray[index]);
            result += intValue * ratio;
            ratio *= 36;
            index--;
        }
        return result;
    }

    /**
     * 将一个int值转换成[0-9a-zA-Z]的char
     *
     * @param i int值
     * @return 如果未找到匹配的char，则返回默认的char: '0'
     */
    private static char int2Char(int i) {
        if (i < 10) {
            return (char) (i + '0');
        }
        i -= 10;
        if (i < 26) {
            return (char) (i + 'a');
        }
        i -= 26;
        if (i < 26) {
            return (char) (i + 'A');
        }
        return '0';
    }

    /**
     * 将一个char[0-9a-zA-Z]转换成62进制的int值
     *
     * @param c CHAR
     * @return 0-61的值
     */
    private static int char2Int(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'z') {
            return c + 10 - 'a';
        }
        if (c >= 'A' && c <= 'Z') {
            return c + 10 + 26 - 'A';
        }
        return 0;
    }
}
