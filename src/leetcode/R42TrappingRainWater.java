package leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author 喵粮都输光了
 * @date 2021/11/29
 * @description 42.接雨水 https://leetcode-cn.com/problems/trapping-rain-water/
 */
public final class R42TrappingRainWater {

    /**
     * 动态规划思路的简化法(相当于双指针思路)，trap2的简化
     *
     * @param height 数组
     * @return 雨水
     */
    public static int trap1(int[] height) {
        int result = 0;
        int leftHeight = 0;
        int rightHeight = 0;
        // 例如height为0,1,0,2,1,0,1,3,2,1,2,1
        for (int i = 0; i < height.length; ++i) {
            // 从左到右遍历，当前位置的值取当前位置和其左边所有位置比较的最大的值，取出的值为0 1 1 2 2 2 2 3 3 3 3 3
            leftHeight = Math.max(leftHeight, height[i]);
            // 从右到左遍历，当前位置的值取当前位置和其右边所有位置比较的最大的值，取出的值为1 2 2 2 3 3 3 3 3 3 3 3
            rightHeight = Math.max(rightHeight, height[height.length - i - 1]);
            // 这里本来是取两个数组相同下标位置的最小值进行计算的，但是这里从右到左的遍历是反方向的，
            // 所以换个计算方法，把两个数组相同下标位置的值加起来再减去当前位置的高度，就可以获得左右两边最大各自可能获得的雨水最大值
            // 可以这样思考，反正全部加起来了，那么也就是说相当于第一格等于从左到右遍历的第一个和从右到左遍历的最后一个相加，也就是同位置相加减去数组的最高高度
            // 再往简单说就是两数相加减去最大的数就能得到两数中最小的数，我们已经知道了最大的数就是数组中最大的数，那么自然可以获取答案
            result += leftHeight + rightHeight - height[i];
        }
        // 在这里leftHeight成了height中最高的高度，用例子来就是3，使用上述加起来的雨水最大值减去这个高度*长度就可以得到最小值的概念
        return result - height.length * leftHeight;
    }

    /**
     * 动态规划思路：从左到右遍历，当前位置的值取当前位置和其左边所有位置比较的最大的值
     * 从右到左遍历，当前位置的值取当前位置和其右边所有位置比较的最大的值
     * 将两个数组进行比较，取两者最小的值再减去原来数组当前下标的值后的和就是答案
     *
     * @param height 数组
     * @return 雨水
     */
    public static int trap2(int[] height) {
        int len = height.length;
        if (len == 0) {
            return 0;
        }
        // 从左到右遍历，当前位置的值取当前位置和其左边所有位置比较的最大的值
        int[] leftMax = new int[len];
        leftMax[0] = height[0];
        for (int i = 1; i < len; ++i) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        // 从右到左遍历，当前位置的值取当前位置和其右边所有位置比较的最大的值
        int[] rightMax = new int[len];
        rightMax[len - 1] = height[len - 1];
        for (int i = len - 2; i >= 0; --i) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        // 将两个数组进行比较，取两者最小的值再减去原来数组当前下标的值后的和就是答案
        int result = 0;
        for (int i = 0; i < len; ++i) {
            result += (Math.min(leftMax[i], rightMax[i]) - height[i]);
        }
        return result;
    }

    /**
     * 单调栈思路：从左到右遍历数组，将下标存入数组，当遇到当前下标的值大于上一个下标的值时，就可以计算上一个下标接住的雨水大小，
     * 继续用当前下标与上个或上上个等等下标值比较，就可以得出每一个下标处接水的大小
     *
     * @param height 数组
     * @return 雨水
     */
    public static int trap3(int[] height) {
        int result = 0;
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < height.length; ++i) {
            // 栈不为空，且当前值大于上一个下标的值时，计算接水大小
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                // 弹出上一个，如果没有上上个下标，说明是第一个高度，直接继续下一个循环即可
                // 例如01，存入0，碰到1弹出0，1肯定没有雨水
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                // 例如102，当前是2，top就是0，计算水量还要取0左边的值，也就是1，通过用0两边的最小值减去0的高度就是0所在位置得到的雨水量
                int currentHeight = Math.min(height[stack.peek()], height[i]) - height[top];
                // 计算出了当前高度，还要计算雨水量的宽度，如21012，如果当前是第二个1的位置，会计算0的位置水量是1，然后栈中就生下了21，
                // 这时候到了第二个2的位置，用当前的2和1左边的2比较取最小的也就是2，再减去1所在位置的大小，得到的值-1就是他们之间的空格宽度
                // 还要计算当前2和上一个2的位置宽度，乘以水量就是所有的
                result += (currentHeight * (i - stack.peek() - 1));
            }
            // 栈空直接下标入栈
            stack.push(i);
        }
        return result;
    }

}