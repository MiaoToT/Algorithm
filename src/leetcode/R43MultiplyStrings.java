package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/2
 * @description 43.字符串相乘 https://leetcode-cn.com/problems/multiply-strings/
 */
public final class R43MultiplyStrings {

    /**
     * 加法思路：例如123*456，123*6+123*50+123*400
     *
     * @param num1 数1
     * @param num2 数2
     * @return 结果
     */
    public static String multiply1(String num1, String num2) {
        String result = "0";
        if (num1.equals(result) || num2.equals(result)) {
            return result;
        }
        for (int i = num2.length() - 1; i >= 0; --i) {
            StringBuilder sum = new StringBuilder();
            int carry = 0;
            // 给当前的位数补0，比如567，7没有0，6补1个0，5补2个0，相当于最后的答案*10
            sum.append("0".repeat(num2.length() - 1 - i));
            // 获取乘数的值
            int number2 = num2.charAt(i) - '0';
            for (int j = num1.length() - 1; j >= 0; --j) {
                int number1 = num1.charAt(j) - '0';
                int tempSum = number1 * number2 + carry;
                sum.append(tempSum % 10);
                carry = tempSum / 10;
            }
            if (carry != 0) {
                sum.append(carry);
            }
            result = R415AddStrings.addStrings(result, sum.reverse().toString());
        }
        return result;
    }

    /**
     * 乘法思路：例如123*456，(3*6+3*50+3*400)+(20*6+20*50+20*400)+(100*6+100*50+100*400)
     *
     * @param num1 数1
     * @param num2 数2
     * @return 结果
     */
    public static String multiply2(String num1, String num2) {
        String zero = "0";
        if (num1.equals(zero) || num2.equals(zero)) {
            return zero;
        }
        // 用于存储乘积，减少字符串操作(两数相乘获得的乘积长度在m+n-1到m+n之间，取m+n作为长度)
        int[] resultArray = new int[num1.length() + num2.length()];
        for (int i = num1.length() - 1; i >= 0; --i) {
            // 获取乘数的值
            int number1 = num1.charAt(i) - '0';
            for (int j = num2.length() - 1; j >= 0; --j) {
                int number2 = num2.charAt(j) - '0';
                // 从最右边的位置开始存储，然后碰到有值的位置两者相加
                resultArray[i + j + 1] += number1 * number2;
            }
        }
        // 处理进位标识
        for (int i = num1.length() + num2.length() - 1; i > 0; --i) {
            resultArray[i - 1] += resultArray[i] / 10;
            resultArray[i] %= 10;
        }
        // 数组转字符，舍弃最高位为0的情况
        StringBuilder result = new StringBuilder();
        for (int i = (resultArray[0] == 0) ? 1 : 0; i < num1.length() + num2.length(); ++i) {
            result.append(resultArray[i]);
        }
        return result.toString();
    }

}