package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/29
 * @description 41.缺失的第一个正数 https://leetcode-cn.com/problems/first-missing-positive/
 */
public final class R41FirstMissingPositive {

    /**
     * 标记法：先把不存在的数都置为数组长度+1，因为一个数组长度为len的数组中出现的最小正整数只可能在[1~n+1]之间。
     * 然后用数组下标和负号进行标记，当数组在[1~len]之间，就把对应的下标所在位置的数字标记为负数，
     * 最后取第一个未被标记为负数的值的下标+1就是最终答案
     *
     * @param nums 数组
     * @return 不存在的最小正整数
     */
    public static int firstMissingPositive1(int[] nums) {
        int len = nums.length;
        // 把不符合的数字(负无穷,0]全部置为数组长度+1
        for (int i = 0; i < len; ++i) {
            if (nums[i] <= 0) {
                nums[i] = len + 1;
            }
        }
        // 这一步相当于是标记，标记1~len中出现过的数字
        for (int i = 0; i < len; ++i) {
            int num = Math.abs(nums[i]);
            // 取出小于或等于数组长度的数，并且把这些数-1的位置的数变为负数
            if (num <= len) {
                nums[num - 1] = -Math.abs(nums[num - 1]);
            }
        }
        // 取出靠前的大于0的数就是缺失的数的下标
        for (int i = 0; i < len; ++i) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        // 如果不存在缺失的数的话就是数组长度+1
        return len + 1;
    }

    /**
     * 交换法：遍历数组，每次将一个[1~len]之间的数放在数组的正确位置，也就是交换位置，
     * 最后遍历数组，如果当前数不等于当前下标+1的话就是不存在的数
     *
     * @param nums 数组
     * @return 不存在的最小正整数
     */
    public static int firstMissingPositive2(int[] nums) {
        int len = nums.length;
        // 遍历数组，把[1~len]之间的数放在数组的正确位置
        for (int i = 0; i < len; ++i) {
            // 当要交换位置的数相同时可能会死循环，所以nums[nums[i]-1]!=nums[i]
            while (nums[i] > 0 && nums[i] <= len && nums[nums[i] - 1] != nums[i]) {
                // 交换数
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
        }
        // 遍历数组当前数不等于当前下标+1的话就是不存在的数
        for (int i = 0; i < len; ++i) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        // 如果不存在缺失的数的话就是数组长度+1
        return len + 1;
    }

}