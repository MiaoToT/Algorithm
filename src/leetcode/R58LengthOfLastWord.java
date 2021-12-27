package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/27
 * @description 58.最后一个单词的长度 https://leetcode-cn.com/problems/length-of-last-word/
 */
public final class R58LengthOfLastWord {

    public static int lengthOfLastWord(String s) {
        int result = 0;
        for (int i = s.length() - 1; i >= 0; --i) {
            if (result == 0 && s.charAt(i) == ' ') {
                continue;
            }
            if (s.charAt(i) == ' ') {
                return result;
            }
            ++result;
        }
        return result;
    }

}