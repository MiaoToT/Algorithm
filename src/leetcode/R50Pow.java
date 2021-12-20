package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/20
 * @description 50.Pow(x, n) https://leetcode-cn.com/problems/powx-n/
 */
public final class R50Pow {

    /**
     * 快速幂(递归处理)：对上一个值进行平方x->x<sup>2</sup>->x<sup>4</sup>，这样就不需要对第一个数乘以(n-1)次了
     *
     * @param x 值
     * @param n 次方
     * @return double
     */
    public static double myPow1(double x, int n) {
        // 处理次方为负的情况，Integer.MIN_VALUE取反会溢出
        return n >= 0 ? selfMultiply1(x, n) : 1 / selfMultiply1(x, -(long) n);
    }

    private static double selfMultiply1(double x, long n) {
        if (n == 0) {
            return 1.0;
        }
        // 想要计算x^n，计算出y=x^(n/2)，最后x^n=y*y，也可能需要额外乘以一个x，最后补充x比较方便，一开始不知道要不要补充x
        double y = selfMultiply1(x, n / 2);
        // 偶数开平方，奇数开平方后乘原来的值，例如x的三次，y=x*x，x的三次=y*x
        return n % 2 == 0 ? y * y : y * y * x;
    }

    /**
     * 快速幂(迭代处理)：例如x^77，其中77的二进制为1001101，对其按位取次方则为
     * 1    0   0    1   1    0   1
     * x^64 x^32 x^16 x^8 x^4 x^2 x^1
     * 把所有的1相对应的值取出相乘就是x^77的解。x^77=x^64 * x^8 * x^4 * x^1
     *
     * @param x 值
     * @param n 次方
     * @return double
     */
    public static double myPow2(double x, int n) {
        // 处理次方为负的情况，Integer.MIN_VALUE取反会溢出
        return n >= 0 ? selfMultiply1(x, n) : 1 / selfMultiply1(x, -(long) n);
    }

    private static double selfMultiply2(double x, long n) {
        double result = 1;
        while (n > 0) {
            if (n % 2 == 1) {
                // 如果n二进制表示的最低位为1，就把结果乘到最终结果中
                result *= x;
            }
            x *= x;
            n /= 2;
        }
        return result;
    }

}