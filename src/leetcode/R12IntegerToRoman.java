package leetcode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 喵粮都输光了
 * @date 2021/11/4
 * @description 12.整数转罗马数字 https://leetcode-cn.com/problems/integer-to-roman/
 */
public final class R12IntegerToRoman {

    private final static Map<Integer, String> SYMBOL_MAP = new LinkedHashMap<>() {{
        put(1000, "M");
        put(900, "CM");
        put(500, "D");
        put(400, "CD");
        put(100, "C");
        put(90, "XC");
        put(50, "L");
        put(40, "XL");
        put(10, "X");
        put(9, "IX");
        put(5, "V");
        put(4, "IV");
        put(1, "I");
    }};

    /**
     * 思路：枚举组合，4，9，40，90，400，900。然后用所给num从大到小的减去就可以获得最终的罗马数字
     *
     * @param num num
     * @return 罗马数字
     */
    public static String intToRoman(int num) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, String> entry : SYMBOL_MAP.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            while (num >= key) {
                num -= key;
                result.append(value);
            }
            if (num == 0) {
                break;
            }
        }
        return result.toString();
    }

}
