package leetcode;

import java.util.*;

/**
 * @author 喵粮都输光了
 * @date 2021/12/19
 * @description 49.字母异位词分组 https://leetcode-cn.com/problems/group-anagrams/
 */
public final class R49GroupAnagrams {

    /**
     * 字符串排序后作为哈希表的key
     *
     * @param strs 字符串数组
     * @return 同类集合
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> result = new HashMap<>(strs.length);
        for (String str : strs) {
            char[] keyChars = str.toCharArray();
            Arrays.sort(keyChars);
            String key = Arrays.toString(keyChars);
            List<String> tempResult = result.getOrDefault(key, new ArrayList<>());
            tempResult.add(str);
            result.put(key, tempResult);
        }
        return result.values().stream().toList();
    }

}