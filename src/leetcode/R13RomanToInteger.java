package leetcode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 喵粮都输光了
 * @date 2021/11/5
 * @description 13.罗马数字转整数 https://leetcode-cn.com/problems/roman-to-integer/
 */
public final class R13RomanToInteger {

    private final static Map<Character, Integer> SYMBOL_MAP = new LinkedHashMap<>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    public static int romanToInt(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); ++i) {
            int value = SYMBOL_MAP.get(s.charAt(i));
            // 如果当前字符比下一个字符大则可以直接相加，如果当前字符小于下一个字符
            // 例如 XIV，X大于I，+X，I小于V，-I，V后面没有了+V，也就是X-I+V=10-1+5=14
            result = i < s.length() - 1 && value < SYMBOL_MAP.get(s.charAt(i + 1)) ? result - value : result + value;
        }
        return result;
    }

}
