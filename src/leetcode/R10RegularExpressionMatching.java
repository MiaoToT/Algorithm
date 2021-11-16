package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/3
 * @description 10.正则表达式匹配 https://leetcode-cn.com/problems/regular-expression-matching/
 */
public final class R10RegularExpressionMatching {

    private final static char ASTERISK = '*';
    private final static char DOT = '.';

    /**
     * 动态规划处理：
     *
     * @param s 字符串
     * @param p 规则串
     * @return boolean
     */
    public static boolean isMatch(String s, String p) {
        // 用于存储状态，第一个[]是s的下标，第二个[]是p的下标，判断该s的字符和p的字符是否匹配，这里下标都增了1，0作为空字符串的匹配，处理时需要注意
        boolean[][] status = new boolean[s.length() + 1][p.length() + 1];
        // 0,0作为边界，对两个空字符串进行匹配
        status[0][0] = true;
        // 注意status的下标都增了1
        for (int i = 0; i < s.length() + 1; ++i) {
            for (int j = 1; j < p.length() + 1; ++j) {
                if (p.charAt(j - 1) == ASTERISK) {
                    // 如果j-1为星号，那么首先设置当前i和j的状态为星号上上个字符的状态，比如a(b*)，这里把b*当成了空串，
                    // 当前字符和b*的前一个字符a进行比较看是否匹配。如果星号前的字符与当前字符不匹配则直接就当空串进行下一次的比较
                    // 简单说这里就是重复0次的情况
                    status[i][j] = status[i][j - 2];
                    // 然后当前字符和规则串中星号的上一个字符进行比较，看是否匹配
                    if (matches(s, p, i, j - 1)) {
                        // 这里看将(b*)作为空串以及xxx其中任意一个满足的了就算当前状态匹配上了
                        // xxx情况比如字符串a[a]a和规则串a[*]，字符串当前字符和规则串星号上一个字符匹配的时候，
                        // 还可能出现一种情况，规则串的这个*匹配了多个字符串中的字符，这里是字符串中第一个a和模式串的*匹配上了
                        // 这里不用担心j不存在，因为字符串每一个字符都会遍历一遍规则串
                        status[i][j] = status[i][j] || status[i - 1][j];
                    }
                } else {
                    // 如果不是星号直接进行下标字符的匹配
                    if (matches(s, p, i, j)) {
                        // 匹配成功了之后还要看上一个字符是否匹配成功
                        // 这里需要注意，如aaa，a*两个进行操作的时候，i和j下标为2和1，也就是a[a]a和[a]*比较的时候匹配成功，
                        // 但是需要取[a]aa和[]a*的比较结果，也就是a和空字符串的比较肯定是false，所以i和j下标为2和1就是false
                        status[i][j] = status[i - 1][j - 1];
                    }
                }
            }
        }
        return status[s.length()][p.length()];
    }

    /**
     * 对应下标字符匹配
     *
     * @param s 字符串
     * @param p 规则串
     * @param i s的下标，i多加了1需要注意
     * @param j p的下标，j多加了1需要注意
     * @return boolean
     */
    private static boolean matches(String s, String p, int i, int j) {
        // 这里跳过所有i为0的匹配，因为当i为0的时候代表s是空串，和任何j为1的匹配都是不可能匹配上的，而j为0已经设置为true了
        if (i == 0) {
            return false;
        }
        // 如果规则串下标字符为点，则直接算匹配成功
        if (p.charAt(j - 1) == DOT) {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }

}
