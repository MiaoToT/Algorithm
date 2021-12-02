package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/2
 * @description 415.字符串相加 https://leetcode-cn.com/problems/add-strings/
 */
public final class R415AddStrings {

    /**
     * 有一点需要注意，StringBuilder的insert比append慢多了，insert(0,str)比append之后把整个字符串反转都慢
     *
     * @param num1 数1
     * @param num2 数2
     * @return 和
     */
    public static String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0 || carry != 0) {
            int number1 = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int number2 = (j >= 0) ? num2.charAt(j) - '0' : 0;
            int sum = number1 + number2 + carry;
            result.append(sum % 10);
            carry = sum / 10;
            --i;
            --j;
        }
        result.reverse();
        return result.toString();
    }

}