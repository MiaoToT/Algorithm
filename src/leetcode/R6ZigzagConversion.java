package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/10/30
 * @description 6.Z字形变换 https://leetcode-cn.com/problems/zigzag-conversion/
 */
public final class R6ZigzagConversion {

    public static String convert(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
        List<StringBuilder> store = new ArrayList<>();
        for (int i = 0; i < numRows; ++i) {
            store.add(new StringBuilder());
        }
        boolean downFlag = false;
        int storeIndex = 0;
        for (char c : s.toCharArray()) {
            store.get(storeIndex).append(c);
            if (storeIndex == 0 || storeIndex == numRows - 1) {
                // 到达最上面或最下面，转向
                downFlag = !downFlag;
            }
            // 下加，上减
            storeIndex += downFlag ? 1 : -1;
        }
        StringBuilder result = new StringBuilder();
        store.forEach(result::append);
        return result.toString();
    }

}
