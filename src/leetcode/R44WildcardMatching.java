package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/2
 * @description 44.通配符匹配 https://leetcode-cn.com/problems/wildcard-matching/
 */
public final class R44WildcardMatching {

    private static final char ASTERISK = '*';
    private static final char QUESTION_MARK = '*';

    /**
     * 动态规划
     *
     * @param s 主串
     * @param p 模式串
     * @return 是否匹配
     */
    public static boolean isMatch1(String s, String p) {
        // 表示s和p对应位置是否匹配，因为0表示空串，所以长度都加一
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        // 两个空串匹配算成功
        dp[0][0] = true;
        // 这里处理字符串为空，p不为空的边界情况，当p只有星号时候才能算匹配成功，否则算失败
        for (int i = 1; i < p.length() + 1; ++i) {
            // 因为多加了1，所以这里要注意减去1
            if (p.charAt(i - 1) == ASTERISK) {
                dp[0][i] = true;
            } else {
                break;
            }
        }
        // 开始匹配
        for (int i = 1; i < s.length() + 1; ++i) {
            for (int j = 1; j < p.length() + 1; ++j) {
                if (p.charAt(j - 1) == ASTERISK) {
                    // 是星号的话分两种情况：
                    // 一种是星号为空，看s的当前字符是否和模式串的上一个字符匹配了；
                    // 另一种是星号可能匹配了很多个的情况，看s上一个字符是否和p当前字符匹配。
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (p.charAt(j - 1) == QUESTION_MARK || s.charAt(i - 1) == p.charAt(j - 1)) {
                    // 如果两个字符匹配，或者p当前字符为?则算成功(前面如果失败了，这个也算失败)
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        // 最终位置就是匹配的结果
        return dp[s.length()][p.length()];
    }

    /**
     * 贪心算法(暴力匹配)：假设模式串为*x*y*...*z，如果在主串中首先找到了x，再找到了y...z，那么主串和模式串就是匹配的
     *
     * @param s 主串
     * @param p 模式串
     * @return 是否匹配
     */
    public static boolean isMatch2(String s, String p) {
        int sRight = s.length();
        int pRight = p.length();
        // 先将字符串前后不是星号处理成字符串前后都是星号的情况
        while (sRight > 0 && pRight > 0 && p.charAt(pRight - 1) != ASTERISK) {
            if (charMatch(s.charAt(sRight - 1), p.charAt(pRight - 1))) {
                --sRight;
                --pRight;
            } else {
                return false;
            }
        }
        // 如果p匹配完了说明是空的，这时候就要看s是不是还有字符了
        if (pRight == 0) {
            return sRight == 0;
        }
        // 表示s和p的下标
        int sIndex = 0;
        int pIndex = 0;
        // 记录找到的x或y或z的起始下标对应的s和p的位置
        int sRecord = -1;
        int pRecord = -1;
        // 开始暴力匹配
        while (sIndex < sRight && pIndex < pRight) {
            if (p.charAt(pIndex) == ASTERISK) {
                // 如果是星号说明找到了x或y或z，用record记录一下找到的位置，并且模式串跳过星号继续匹配
                ++pIndex;
                sRecord = sIndex;
                pRecord = pIndex;
            } else if (charMatch(s.charAt(sIndex), p.charAt(pIndex))) {
                // 如果字符匹配了，则递进下标
                ++sIndex;
                ++pIndex;
            } else if (sRecord != -1 && sRecord + 1 < sRight) {
                // 如果字符不匹配，那么需要重新寻找x或y或z
                // 首先回退到标记位置，因为标记位置的主串字符不匹配，所以跳过该位置字符后开始暴力匹配
                // 如果s的记录起始位置为-1，说明退无可退了匹配是失败的，或者主串已经没有下一个字符可以匹配了也是失败的
                ++sRecord;
                sIndex = sRecord;
                pIndex = pRecord;
            } else {
                // 如果不匹配并且下一个起始位置不存在，那么匹配失败(也就是上一个条件的情况)
                return false;
            }
        }
        // 最后判断下模式串剩余的字符是不是都是星号
        return allStars(p, pIndex, pRight);
    }

    /**
     * 判断p剩余的字符是不是都是星号
     *
     * @param p     模式串
     * @param left  p的左边界
     * @param right p的有边界(不包含)
     * @return 是否都是星号
     */
    private static boolean allStars(String p, int left, int right) {
        for (int i = left; i < right; ++i) {
            if (p.charAt(i) != ASTERISK) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符匹配
     *
     * @param sChar 主串字符
     * @param pChar 模式串字符
     * @return 是否匹配成功
     */
    private static boolean charMatch(char sChar, char pChar) {
        return sChar == pChar || pChar == QUESTION_MARK;
    }

}