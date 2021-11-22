package leetcode;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 喵粮都输光了
 * @date 2021/11/21
 * @description 30.串联所有单词的子串 https://leetcode-cn.com/problems/substring-with-concatenation-of-all-words/
 */
public final class R30SubstringWithConcatenationOfAllWords {

    /**
     * 滑动窗口
     *
     * @param s     需要解析的字符串
     * @param words 解析所需的单词组
     * @return 匹配上的起始下标地址，多个
     */
    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new LinkedList<>();
        if (s == null || s.isBlank() || words == null || words.length == 0) {
            return result;
        }
        int wordLen = words[0].length();
        Map<String, Integer> totalWordMap = new LinkedHashMap<>();
        // 需要比较的字符存入hashMap，到时候直接用hashMap比较即可
        for (String word : words) {
            totalWordMap.put(word, totalWordMap.getOrDefault(word, 0) + 1);
        }
        // 每次移动一个单词，这里有一点需要注意，比如要匹配ab，但是字符串为sabs，如果从一开始匹配怎么都匹配不上，所需需要对单词长度进行循环偏移变量，
        // 如从a开始abs，ab就匹配上了，偏移了一位
        for (int i = 0; i < wordLen; ++i) {
            int left = i, right = i;
            Map<String, Integer> currentWindowWordMap = new LinkedHashMap<>();
            while (right + wordLen <= s.length()) {
                // 从右窗口开始获取一个单词
                String word = s.substring(right, right + wordLen);
                right += wordLen;
                // 如果该单词不存在所需要的单词列表中，则匹配失败，需要移动左窗口，并且清空窗口内暂存的map，重新进行统计
                if (!totalWordMap.containsKey(word)) {
                    left = right;
                    currentWindowWordMap.clear();
                } else {
                    // 统计当前字串该单词出现的次数
                    currentWindowWordMap.put(word, currentWindowWordMap.getOrDefault(word, 0) + 1);
                    // 如果当单词加入当前窗口后，窗口内该单词的个数大于单词列表中该单词的个数，则从左窗口开始删除单词直到删除到单词个数正常为止
                    // 例如单词列表为[foo,bar,foo,the]，当前窗口|foobarfoobar|foothe，右移窗口foo|barfoobar|foothe，
                    // 发现还是有问题，继续右移窗口foobar|foobar|foothe正常，循环以上操作即可
                    // 简单说就是碰到单词超过的时候，不断移动左窗口，直到单词数正常为止即可，因为单词数量超过的话，不管怎么匹配都是失败的，直接舍弃到超过单词第一次出现的位置为止
                    while (currentWindowWordMap.getOrDefault(word, 0) > totalWordMap.getOrDefault(word, 0)) {
                        // 左窗口右移，减去该单词出现次数，减去当前窗口单词个数，
                        String leftWord = s.substring(left, left + wordLen);
                        currentWindowWordMap.put(leftWord, currentWindowWordMap.getOrDefault(leftWord, 0) - 1);
                        left += wordLen;
                    }
                }
                if (currentWindowWordMap.equals(totalWordMap)) {
                    result.add(left);
                }
            }
        }
        return result;
    }

}