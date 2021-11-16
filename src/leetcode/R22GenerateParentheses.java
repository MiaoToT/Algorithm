package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/11/16
 * @description 22.括号生成 https://leetcode-cn.com/problems/generate-parentheses/
 */
public final class R22GenerateParentheses {

    /**
     * @param n 括号对数
     * @return 符合要求的括号
     */
    public static List<String> generateParenthesis(int n) {
        List<String> result = new LinkedList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    /**
     * 回溯：左括号小于n则可以加入左括号，右括号小于左括号数量则可以加入右括号，然后判断是否到达n*2的长度，到达了就是符合要求的组合
     *
     * @param result           最终结果
     * @param current          当前括号
     * @param leftBracketNum   current中左括号的个数
     * @param rightBracketNum  current中右括号的个数
     * @param singleBracketNum 左或右括号最大的个数
     */
    private static void backtrack(List<String> result, StringBuilder current, int leftBracketNum, int rightBracketNum, int singleBracketNum) {
        if (current.length() == singleBracketNum * 2) {
            result.add(current.toString());
            return;
        }
        if (leftBracketNum < singleBracketNum) {
            current.append('(');
            backtrack(result, current, leftBracketNum + 1, rightBracketNum, singleBracketNum);
            // 跑完要把当前加入的删除，比如只有两对括号，首先(())，((，()，这样
            current.deleteCharAt(current.length() - 1);
        }
        if (rightBracketNum < leftBracketNum) {
            current.append(')');
            backtrack(result, current, leftBracketNum, rightBracketNum + 1, singleBracketNum);
            current.deleteCharAt(current.length() - 1);
        }
    }

}
