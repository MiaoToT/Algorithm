package leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author 喵粮都输光了
 * @date 2021/11/23
 * @description 32.最长有效括号 https://leetcode-cn.com/problems/longest-valid-parentheses/
 */
public final class R32LongestValidParentheses {

    /**
     * 动态规划
     *
     * @param s 括号字符串
     * @return 最长有效括号子串的长度
     */
    public static int longestValidParentheses1(String s) {
        int result = 0;
        // 存储以下标i对应字符结尾的最长有效括号的长度
        int[] dp = new int[s.length()];
        for (int i = 1; i < s.length(); ++i) {
            // 有效结尾必然是)，以(结尾的一定是错误的，所以(对应的dp一定是0
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    // 如果上一个字符是(，也就是......()，可以推出当前的dp值为上上个位置的dp值+2
                    // 这里需要注意边界值，小心越界
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    // 1.假设dp[i-1]为2，也就是()，当前i的位置为)，也就是())，那么要让公式成立，必须要有一个(())
                    // 2.i-dp[i-1]>0是防止越界，比如 dp[i-1]为2，i为2，也就是())，如果i-dp[i-1]=0就不可能存在(())了，可以直接排除
                    // 3.当前dp值为上一个位置的dp值+2(当前位置一对有小括号)+如果当前位置减去上一个位置的dp值大于等于2，
                    // 说明前面还有更多的括号，取当前位置-2有效括号-上个位置dp值得到__(...)下划线位置的有效括号对数
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                result = Math.max(result, dp[i]);
            }
        }
        return result;
    }

    /**
     * 栈思路：碰到(直接存入栈，碰到)就弹出栈顶元素，如果栈为空存入)，
     * 如果不为空计算最长有效括号子串的长度，也就是当前下标减去弹出栈顶元素的下标。
     * 为了方便处理这里在一开始存入-1，加入()，栈中就是-1,(，当碰到)淡出(，然后用)的下标1减去存在栈中的-1的值，2就是有效长度
     *
     * @param s 括号字符串
     * @return 最长有效括号子串的长度
     */
    public static int longestValidParentheses2(String s) {
        int result = 0;
        Deque<Integer> stack = new LinkedList<>();
        // 默认存入-1，方便边界值的处理，例如-1()，)和(抵消，)的下标1减去-1为2，有效长度2
        stack.push(-1);
        for (int i = 0; i < s.length(); ++i) {
            // 碰到(直接存入
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                // 碰到)弹出栈顶元素，默认匹配上了
                stack.pop();
                if (stack.isEmpty()) {
                    // 如果栈空了，存入(，防止越界，也方便有效括号对数的计算
                    stack.push(i);
                } else {
                    // 如果栈不为空可以直接进行有效括号对数计算例如-1()，)与(抵消了，用)的下标1减去栈中的-1为2
                    result = Math.max(result, i - stack.peek());
                }
            }
        }
        return result;
    }

    /**
     * 贪心算法：从左到右遍历字符串，期间维护两个计数器left和right，
     * 碰到(，left+1，碰到)，right+1，如果right大于left直接，left和right置为0，相当于舍弃当前位置以前的字符。
     * 碰到left和right相等时计算当前有效字符串的长度并记录最长子字符串长度。
     * 这样的话碰到left大于left的情况是没办法处理的，我们从右到左遍历字符串，判断条件取反即可解决。
     *
     * @param s 括号字符串
     * @return 最长有效括号子串的长度
     */
    public static int longestValidParentheses3(String s) {
        int result = 0;
        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(') {
                ++left;
            } else {
                ++right;
            }
            if (left == right) {
                // 否则计算有效括号长度，左(右)括号个数*2
                result = Math.max(result, 2 * right);
            } else if (right > left) {
                // 右括号比左括号多直接舍弃之前的左括号和右括号
                left = 0;
                right = 0;
            }
        }
        // 处理右括号个数大于左括号个数的情况
        left = 0;
        right = 0;
        for (int i = s.length() - 1; i >= 0; --i) {
            if (s.charAt(i) == ')') {
                ++right;
            } else {
                ++left;
            }
            if (left == right) {
                result = Math.max(result, 2 * right);
            } else if (left > right) {
                // 这里条件要取反，因为是处理(()的情况，所以左括号大于右括号的个数的时候，舍弃之前的左右括号
                left = 0;
                right = 0;
            }
        }
        return result;
    }

}