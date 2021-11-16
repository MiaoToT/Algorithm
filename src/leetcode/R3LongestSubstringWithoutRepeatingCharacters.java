package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 喵粮都输光了
 * @date 2021/10/23
 * @description 3.无重复字符的最长子串 https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 */
public final class R3LongestSubstringWithoutRepeatingCharacters {

    public static int lengthOfLongestSubstring1(String s) {
        // 核心思路：窗口，假设abcabcbb，第一个最长abc，这时候删除第一个字符，右边不回退，
        //          看看新的字符重复吗，不重复加入，重复的话就比较长度然后继续循环
        //          (这里比如abcc，最长abc，删除a，发现bcc重复，那么这次的就是bc，小于abc舍弃继续下一个，右边的指针是不回退的)
        // HashSet的contains比String的contains要快一点
        Set<Character> hashSet = new HashSet<>();
        int longestSubstringLength = 0;
        for (int left = 0, right = 0; left < s.length(); ++left) {
            if (left != 0) {
                hashSet.remove(s.charAt(left - 1));
            }
            while (right < s.length() && !hashSet.contains(s.charAt(right))) {
                hashSet.add(s.charAt(right));
                ++right;
            }
            longestSubstringLength = Math.max(longestSubstringLength, right - left);
        }
        return longestSubstringLength;
    }

    public static int lengthOfLongestSubstring2(String s) {
        // 核心思路：窗口，但是不使用Set，并且假设字符为ascii字符
        // 记录字符上一次出现的位置+1的位置
        int[] last = new int[128];
        int longestSubstringLength = 0;
        // 这里left是窗口开始的位置，right是窗口结束的位置
        for (int left = 0, right = 0; right < s.length(); ++right) {
            // 当前字符
            int currentLetter = s.charAt(right);
            // 获取左窗口位置，如abca，当前是第二个a的话，left为b的位置(上一次a出现位置的下一个位置)
            left = Math.max(left, last[currentLetter]);
            longestSubstringLength = Math.max(longestSubstringLength, right - left + 1);
            // 记录当前字符的下一个字符位置，如abcab，如果在第二个a，则a的上一次出现位置在b
            last[currentLetter] = right + 1;
        }
        return longestSubstringLength;
    }

}
