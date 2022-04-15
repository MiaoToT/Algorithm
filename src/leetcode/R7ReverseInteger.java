package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/10/30
 * @description 7.整数反转 https://leetcode-cn.com/problems/reverse-integer/
 */
public final class R7ReverseInteger {

    public static int reverse(int x) {
        int result = 0;
        while (x != 0) {
            // 首先7是2^31-1的个位，-8是-2^31的个位
            // 实际公式为 result>(Integer最大值/10)那肯定不成立；
            // 当result=(Integer最大值/10)时则需要判断x%10的值是否大于7，如果大于就是不成立的。
            // 负数则相反，result<(Integer最小值/10)不成立，当相等时候，判断是否小于-8，小于则不成立
            // 正数条件：result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && x % 10 > 7)
            // 负数条件：result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && x % 10 < -8)
            // 由于Integer的最大最小值最高位为2，当result等于Integer最大或最小值的时候，x反转后的个位肯定是1或者2，而个位%10必定小于7或者大于-8不满足条件，所以不需要后续的判断
            if (result < Integer.MIN_VALUE / 10 || result > Integer.MAX_VALUE / 10) {
                return 0;
            }
            result = result * 10 + x % 10;
            x /= 10;
        }
        return result;
    }

}
