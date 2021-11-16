package leetcode;

import java.util.Map;

/**
 * @author 喵粮都输光了
 * @date 2021/10/30
 * @description 8.字符串转换整数(atoi) https://leetcode-cn.com/problems/string-to-integer-atoi/
 */
public final class R8StringToIntegerAtoi {

    /**
     * 普通解法
     *
     * @param s 字符串
     * @return 值
     */
    public static int myAtoi1(String s) {
        int result = 0;
        s = s.trim();
        if (s.length() == 0) {
            return 0;
        }
        if (!Character.isDigit(s.charAt(0)) && s.charAt(0) != '-' && s.charAt(0) != '+') {
            return 0;
        }
        boolean isMinus = s.charAt(0) == '-';
        // 不是数字直接跳过从1开始
        int index = Character.isDigit(s.charAt(0)) ? 0 : 1;
        while (index < s.length() && Character.isDigit(s.charAt(index))) {
            int num = s.charAt(index) - '0';
            // 这里没有对负数进行判断是有原因的，正数最大的个位数为7，负数最小的个位数为8
            // 当值比7大的时候，如果是判断负数，那肯定就相当于是最小的负数，可以直接取最小值
            // 如果溢出要返回0的话则不能进行这样的操作，详见ReverseInteger7
            if (result > (Integer.MAX_VALUE - num) / 10) {
                return isMinus ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            result = result * 10 + num;
            ++index;
        }
        return isMinus ? -result : result;
    }

    /**
     * 确定有限状态机(deterministic finite automaton, DFA)解法，也可以称为自动机：
     * 程序在每个时刻有一个状态s，每次从序列中输入一个字符c，并根据字符c转移到下一个状态s'。
     * 因此只需建立一个覆盖所有情况的从s与c映射到s'的表格即可解决题目中的问题。
     *
     * @param s 字符串
     * @return 值
     */
    public static int myAtoi2(String s) {
        Automaton automaton = new Automaton();
        for (int i = 0; i < s.length(); ++i) {
            automaton.get(s.charAt(i));
        }
        return automaton.isMinus ? -automaton.result : automaton.result;
    }

    /**
     * 自动状态机
     */
    private static class Automaton {
        public boolean isMinus = false;
        public int result = 0;
        private String state = "start";
        private final Map<String, String[]> table = Map.of(
                "start", new String[]{"start", "signed", "in_number", "end"},
                "signed", new String[]{"end", "end", "in_number", "end"},
                "in_number", new String[]{"end", "end", "in_number", "end"},
                "end", new String[]{"end", "end", "end", "end"}
        );

        public void get(char c) {
            if (Integer.MAX_VALUE == result || Integer.MIN_VALUE == result) {
                return;
            }
            state = table.get(state)[getCol(c)];
            if ("in_number".equals(state)) {
                // 如果是数字直接计算
                int num = c - '0';
                if (result > (Integer.MAX_VALUE - num) / 10) {
                    result = isMinus ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                    return;
                }
                result = result * 10 + num;
            } else if ("signed".equals(state)) {
                // signed状态判断正负
                isMinus = c == '-';
            }
        }

        /**
         * 获取某个状态下，该字符的下一个状态
         *
         * @param c 当前字符
         * @return 下一个状态下标
         */
        private int getCol(char c) {
            if (c == ' ') {
                return 0;
            }
            if (c == '+' || c == '-') {
                return 1;
            }
            if (Character.isDigit(c)) {
                return 2;
            }
            return 3;
        }
    }

}
