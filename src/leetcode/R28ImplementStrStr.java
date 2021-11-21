package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/21
 * @description 28.实现strStr() https://leetcode-cn.com/problems/implement-strstr/
 */
public final class R28ImplementStrStr {

    /**
     * kmp算法求解
     *
     * @param haystack 原串
     * @param needle   需要匹配的子串
     * @return 起始位置下标
     */
    public static int strStr(String haystack, String needle) {
        int sourceLen = haystack.length();
        int patternLen = needle.length();
        if (patternLen == 0) {
            return 0;
        }
        // 计算模式串的前缀表，前缀和后缀匹配，不包含自身；
        // 匹配方式如aabaaab，a与b、aa与ab、aab与aab、aaba与aaab、aabaa与baaab、aabaaa与abaaab
        // 如：a a b a a a b
        //    0 1 0 1 2 2 3
        int[] prefixTable = new int[patternLen];
        // i用于遍历模式串(从1开始是因为第一个字符没有公共前后缀，也就是值一定为0)
        // j用于记录上一个i匹配到的最长公共前后缀的长度(相当于匹配到的前缀的下一项的下标)，例
        // 如aa，i为1的时候，刚进入循环j为0，因为i为0的时候j是0
        for (int i = 1, j = 0; i < patternLen; ++i) {
            // 如果模式串当前字符和最长公共前后缀的下标位置的字符不等则需要不断回退，直到匹配到有公共前缀的项或回到第一个字符
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) {
                j = prefixTable[j - 1];
            }
            // 如果模式串当前字符和最长公共前后缀的下标位置的字符相等，则最长公共前后缀的下标加一
            if (needle.charAt(i) == needle.charAt(j)) {
                ++j;
            }
            // 更新模式串当前字符的最长公共前后缀长度
            prefixTable[i] = j;
        }
        // 计算原字符串的前缀表，当某个位置的前缀函数等于模式串的长度时，说明找到了对应的匹配串，计算下标位置即可
        for (int i = 0, j = 0; i < sourceLen; i++) {
            // 不相等则需要回退最长公共前后缀的下标
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = prefixTable[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            // 当最长公共前后缀的下标和模式串相等时，就是匹配上了，返回下标位置
            if (j == patternLen) {
                // 需要返回下标，而这使用下标减去长度，此时当然要+1
                return i - patternLen + 1;
            }
        }
        return -1;
    }

}