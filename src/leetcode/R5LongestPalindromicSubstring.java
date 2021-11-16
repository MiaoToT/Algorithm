package leetcode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/10/25
 * @description 5.最长回文子串 https://leetcode-cn.com/problems/longest-palindromic-substring/
 */
public final class R5LongestPalindromicSubstring {

    /**
     * 中心拓展算法：奇数从最中间那个数的左右两个数开始左右两边比较，偶数从最中间两个数开始左右两边比较。
     *
     * @param s 字符串
     * @return 最长回文子串
     */
    public static String longestPalindrome1(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); ++i) {
            // 奇数扩散
            int len1 = expandAroundCenter(s, i, i);
            // 偶数扩散
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            // 如果比原来地回文长则记录
            if (len > end - start + 1) {
                // 这里统一处理了奇数和偶数的开始下标
                // 例如：奇数A(ABCBA)，i为C的下标，len为5，去掉奇数最中心的剩下AB|BC，除去2之后刚好可以用i的位置减去
                // 偶数A(ABCCBA)，i为左C的下标，i+1为右C的下标，len为6，正常为(6-2)/2，这里兼容奇数所以(6-1)/2，刚好分为AB|BC，除去2用i的位置减去
                start = i - (len - 1) / 2;
                // 奇数：假设长度为5，5/2刚好就是i右边的数量
                // 偶数：假设长度为6，6/2刚好是i右边的数量(i+1的话则为[(6-1)/2])
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    /**
     * 中心拓展
     *
     * @param s     字符串
     * @param left  扩散左边的初始位置
     * @param right 扩散右边的初始位置
     * @return 扩散的最大长度
     */
    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        // 这里left会多减一个，right会多加一个，所以需要多-1
        return right - left - 1;
    }

    /**
     * 动态规划：用状态机记录，判断所有长度2的是否回文，判断长度3的只用看首尾字母是否相同并且看长度2的回文是否相同，以此类推
     *
     * @param s 字符串
     * @return 最长回文子串
     */
    public static String longestPalindrome2(String s) {
        if (s.length() < 2) {
            return s;
        }
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[s.length()][s.length()];
        // 初始化：所有长度为 1 的子串都是回文串
        for (int i = 0; i < s.length(); ++i) {
            dp[i][i] = true;
        }
        char[] charArray = s.toCharArray();
        // 最大子串开始位置与其结束位置
        int start = 0, end = 0;
        // 按照长度对字符串进行遍历，因为状态机的回文判断长度3的回文需要知道长度为2的是否为回文，例如i...j，那么i和j相等我要知道i-1和j+1是否相等，不断类推
        for (int len = 2; len <= s.length(); ++len) {
            // 枚举左边界，当left为最大时候，right肯定为最大，因为dp的表格智慧记录右上角的三角形，left=right也就是对角线上的数值已经被初始化过了
            for (int left = 0; left < s.length() - 1; ++left) {
                // 由 左边界left 和 字符串长度len 可以确定右边界
                int right = len + left - 1;
                // 如果右边界越界，就可以退出当前循环
                if (right >= s.length()) {
                    break;
                }
                // 记录回文状态，不相等肯定是不对的
                if (charArray[left] != charArray[right]) {
                    dp[left][right] = false;
                } else {
                    // 相等的话则需要判断 left+1和right-1 是否相等，也就是取dp状态机中的值是否为true
                    // 此处 right - left < 3 完整公式为 (right - 1) - (left + 1) + 1 < 2，计算的是去掉首尾字母吼的字符串是否为回文，
                    // 这里长度小于2，不用检查回文，直接设置为true，因为自身(长度为1)一定是回文的，否则进行检查
                    if (right - left < 3) {
                        dp[left][right] = true;
                    } else {
                        dp[left][right] = dp[left + 1][right - 1];
                    }
                }
                // 用状态机判断[left，right]字符串是否是回文的，并且判断其是否比原来的最长回文串长
                if (dp[left][right] && right - left > end - start) {
                    start = left;
                    end = right;
                }
            }
        }
        return s.substring(start, end + 1);
    }

    /**
     * Manacher：动态规划+中心扩散
     * 例如：原始字符串ABAABA，预处理 ->
     * 处理过的字符串          #  A  #  B  #  A  #  A  #  B  #  A  #
     * 下标i                  0  1  2  3  4  5  6  7  8  9 10 11 12
     * 下标i的最长回文子串长度  1  3  1  7  1  3  13 3  1  7  1  3  1   （下标i左边的长度*2 + 1）
     * 原字符串最长回文子串长度 0  1  0  3  0  1  6  1  0  3  0  1  0   （[处理过的最长回文子串长度 - 1] / 2）
     * 原字符串计算逻辑：以#为中心的去掉这个#就能找到剩下的字符都有一个一一对应#；以字符为中心的去掉第一个#号吗，能找到剩下的字符都有一个一一对应的#；所以可以直接-1之后/2
     * <p>
     * ——————————————————————————————————————————————————————————————————————————
     * minLeft          mirror        center        i          maxRight
     * （i和mirror关于center对称；maxRight和minLeft关于center对称；center是maxRight对应的回文中心的下标；用不到minLeft）
     * <p>
     * 通过推到可以得出两个大类：
     * 情况1： i >= maxRight，例如刚开始的i为0和1的位置，只能进行中心扩散
     * 情况2： i < maxRight，有三小类：可以合并成min(maxRight-i, p[mirror])，然后进行扩散
     * ①、p[mirror] < maxRight - i 时，p[i]=p[mirror]
     * ②、p[mirror] == maxRight - i 时，p[i]至少等于maxRight-i，需要从maxRight进行中心扩散
     * ③、p[mirror] > maxRight - i 时，p[i]=maxRight-i
     *
     * @param s 字符串
     * @return 最长回文子串
     */
    public static String longestPalindrome3(String s) {
        // 记录最长回文子串的开始坐标和结束坐标
        int start = 0, end = -1;
        // #号作为特殊字符，原有字符中不能包含#。拼接格式#1#2##
        String split = "#";
        s = MessageFormat.format("#{0}##", String.join(split, s.split("")));
        // 存储历史字符的处理过的最长回文子串的臂长
        List<Integer> armLen = new ArrayList<>();
        int maxRight = -1, center = -1;
        // 开始计算当前位置字符的最长回文串
        for (int i = 0; i < s.length(); ++i) {
            // 当前字符的处理过的最长回文子串的臂长
            int currentArmLen;
            if (i > maxRight) {
                // 情况一：直接当前位置扩散
                currentArmLen = expand(s, i, i);
            } else {
                // 情况二：获取i关于center的对成点mirror，获取mirror的臂长，最后在这个臂长外进行扩散（因为臂长内都已经是回文的了）
                int mirror = center * 2 - i;
                // 获取确定是回文字符串的臂长
                currentArmLen = Math.min(maxRight - i, armLen.get(mirror));
                // 在该臂长上进行扩散，取更大的臂长
                currentArmLen = expand(s, i - currentArmLen, i + currentArmLen);
            }
            armLen.add(currentArmLen);
            // 如果当前字符下标+臂长超过了maxRight，则需要更新 maxRight 和 center 的值
            if (i + currentArmLen > maxRight) {
                center = i;
                maxRight = i + currentArmLen;
            }
            // 如果当前最大回文子串长度比之前的大就更新最大回文长度的开始和结束下标
            if (currentArmLen * 2 + 1 > end - start + 1) {
                start = i - currentArmLen;
                end = i + currentArmLen;
            }
        }
        return s.substring(start, end + 1).replaceAll("#", "");
    }

    /**
     * Manacher算法中心扩展
     *
     * @param s     字符串
     * @param left  值比较的左下标
     * @param right 值比较的右下标
     * @return 原始字符串最长回文长度
     */
    private static int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        // 本身就是right-left-1，这里left会多减一个，right会多加一个，所以需要多-1
        // 因为要计算原始字符串长度，所以在该基础上用公式（[处理过的最长回文子串长度 - 1] / 2）
        return (right - left - 2) / 2;
    }

}
