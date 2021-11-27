package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/27
 * @description 38.外观数列 https://leetcode-cn.com/problems/count-and-say/
 */
public final class R38CountAndSay {

    public static String countAndSay(int n) {
        String result = "1";
        // 递归几次，0不存在，1就是，之后的n都是取的n-1进行计算
        for (int i = 2; i <= n; ++i) {
            // 记录解读信息
            StringBuilder sb = new StringBuilder();
            // 字符开始的位置
            int left = 0;
            // 当前下标，也是结束的标志
            int right = 0;
            // 对上一次的值进行解读
            while (right < result.length()) {
                // 如果当前下标对应的字符串和一开始的下标对应的字符串相同，当前下标继续移动直到不相同位置
                while (right < result.length() && result.charAt(right) == result.charAt(left)) {
                    ++right;
                }
                // 记录该字符个数以及该字符是什么，如111为3个1，31
                sb.append(right - left).append(result.charAt(left));
                // 字符开始的位置调至当前位置开始下一轮的循环
                left = right;
            }
            // 设置该次解读的结果
            result = sb.toString();
        }
        return result;
    }

}