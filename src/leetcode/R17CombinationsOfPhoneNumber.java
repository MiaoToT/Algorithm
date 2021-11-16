package leetcode;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 喵粮都输光了
 * @date 2021/10/21
 * @description 电话号码的字母组合 https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 */
public final class R17CombinationsOfPhoneNumber {

    public static List<String> letterCombinations(String digits) {
        List<String> result = new LinkedList<>();
        if (digits == null || digits.isBlank()) {
            return result;
        }
        Map<Character, String> map = new LinkedHashMap<>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        backtracking(result, map, digits, 0, new StringBuilder());
        return result;
    }

    /**
     * 回溯算法
     *
     * @param result 最终结果
     * @param map    数字与字母对应的map
     * @param digits 需要穷举的字符串
     * @param index  digits的下标
     */
    public static void backtracking(List<String> result, Map<Character, String> map, String digits, int index, StringBuilder stringBuilder) {
        if (index == digits.length()) {
            result.add(stringBuilder.toString());
        } else {
            char mainLetter = digits.charAt(index);
            String letters = map.get(mainLetter);
            for (int i = 0; i < letters.length(); i++) {
                stringBuilder.append(letters.charAt(i));
                backtracking(result, map, digits, index + 1, stringBuilder);
                // 这里stringBuilder是公用的，回来之后需要回退，回溯算法
                stringBuilder.deleteCharAt(index);
            }
        }
    }

}
