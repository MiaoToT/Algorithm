package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/11/21
 * @description 29.两数相除 https://leetcode-cn.com/problems/divide-two-integers/
 */
public final class R29DivideTwoIntegers {

    /**
     * 二分查找
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 值
     */
    public static int divide(int dividend, int divisor) {
        // 被除数边界考虑
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == 1) {
                return Integer.MIN_VALUE;
            } else if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
        }
        // 除数边界考虑
        if (divisor == Integer.MIN_VALUE) {
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        } else if (divisor == 0) {
            return 0;
        }
        // 二分查找，将所有正数取相反数，缩小考虑情况为一种
        boolean reverse = false;
        if (dividend > 0) {
            dividend = -dividend;
            reverse = true;
        }
        if (divisor > 0) {
            divisor = -divisor;
            reverse = !reverse;
        }
//        int result = method1(dividend, divisor);
        int result = method2(dividend, divisor);
        return reverse ? -result : result;
    }

    /**
     * 类二分查找思路：首先不断地将除数乘以2，并将这些结果放入数组中，
     * 然后对数组进行逆序遍历，当遍历到第i项时，如果其大于等于被除数，我们就将答案增加 2^i，并且用被除数减去这一项的值。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 值
     */
    private static int method2(int dividend, int divisor) {
        List<Integer> candidates = new ArrayList<>();
        candidates.add(divisor);
        int result = 0;
        int index = 0;
        // 注意溢出，存入值，除数、除数*2，除数*3这样
        while (candidates.get(index) >= dividend - candidates.get(index)) {
            candidates.add(candidates.get(index) + candidates.get(index));
            ++index;
        }
        // 倒序遍历
        for (int i = candidates.size() - 1; i >= 0; --i) {
            // 碰到数组中某项的值大于等于被除数的时候
            if (candidates.get(i) >= dividend) {
                // 答案加 2^i，并且被除数减去该项的值
                result += (1 << i);
                dividend -= candidates.get(i);
            }
        }
        return result;
    }

    /**
     * 二分查找
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 值
     */
    private static int method1(int dividend, int divisor) {
        int left = 1;
        int right = Integer.MAX_VALUE;
        int result = 0;
        while (left < right) {
            // 防止溢出，这里right和left都是负数，(left+right)/2可能溢出，换成下面的写法
            int mid = left + ((right - left) >> 1);
            // 判断是否比中位数大
            boolean isBiggerThanMid = quickAdd(divisor, mid, dividend);
            if (isBiggerThanMid) {
                result = mid;
                if (mid == Integer.MAX_VALUE) {
                    break;
                }
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    /**
     * 快乘法思路：2^1+2^2+2^3+……+2^n = 2^m(方便写，实际不是2)
     * 类似题目可以看R50
     *
     * @param y 除数
     * @param z 值域的中位数
     * @param x 被除数
     * @return 看x/y的值是否大于mid的值，大于true，小于false
     */
    private static boolean quickAdd(int y, int z, int x) {
        // x 和 y 是负数，z 是正数，这里需要判断 z * y >= x 是否成立
        int result = 0;
        // 每次加除数的值
        int add = y;
        // z是一个边界，最小为0
        while (z > 0) {
            // 如果是奇数，也就是最低位为1时
            if ((z & 1) == 1) {
                // 需要保证 result + add >= x
                if (result < x - add) {
                    return false;
                }
                // 如果是奇数，也就是最低位为1时，需要额外贡献一个初始值，也就是y(除数)
                result += add;
            }
            if (z != 1) {
                // 需要保证 add + add >= x
                if (add < x - add) {
                    return false;
                }
                // 这里因为是2^1+2^2+……(方便写，实际不是2)，后者在前者的基础上乘了2
                add += add;
            }
            // z /= 2 舍去最低为，所以之后只需判断最低位即可
            // z /= 2 舍去最低为，所以之后只需判断最低位即可
            z >>= 1;
        }
        return true;
    }

}