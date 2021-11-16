package leetcode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author 喵粮都输光了
 * @date 2021/11/5
 * @description 20.有效的括号 https://leetcode-cn.com/problems/valid-parentheses/
 */
public final class R20ValidParentheses {

    private final static Map<Character, Character> BRACKETS_MAP = new HashMap<>() {{
        put(')', '(');
        put('}', '{');
        put(']', '[');
    }};

    public static boolean isValid(String s) {
        // 括号成对出现
        if (s.length() % 2 == 1) {
            return false;
        }
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < s.length(); ++i) {
            char currentChar = s.charAt(i);
            if (BRACKETS_MAP.containsKey(currentChar)) {
                if (deque.isEmpty() || !deque.pop().equals(BRACKETS_MAP.get(currentChar))) {
                    return false;
                }
            } else {
                deque.push(currentChar);
            }
        }
        return deque.isEmpty();
    }

}
