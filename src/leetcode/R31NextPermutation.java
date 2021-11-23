package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/22
 * @description 31.下一个排列 https://leetcode-cn.com/problems/next-permutation/
 */
public final class R31NextPermutation {

    /**
     * 思路：
     * 步骤1：nums倒序遍历，找到一个比上一个值小的值标为a；
     * 步骤2：然后再找[a下标+1,nums.length-1]区间一个比a大的值b，同样倒序遍历找；
     * 步骤3：最后交换a和b，然后将b右边的区间(不包括b)反转即可获得下一个排列；
     * 注意：如果步骤1找不到则说明序列是一个降序序列了，直接反转即可获得最小排列。
     *
     * @param nums 数组
     */
    public static void nextPermutation(int[] nums) {
        // 从倒数第二个开始，防止从后往前遍历的时候，下标越界
        int i = nums.length - 2;
        // 从后往前遍历，找到一个不是降序的值。即i的值小于i+1的
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            --i;
        }
        if (i >= 0) {
            // 从后往前遍历，找到比i下标对应的值大的值b
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                --j;
            }
            // 进行交换
            swap(nums, i, j);
        }
        // 反转数组
        reverse(nums, i + 1);
    }

    /**
     * 反转数组
     *
     * @param nums  数组
     * @param start 数组开始的下标
     */
    private static void reverse(int[] nums, int start) {
        int left = start;
        int right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            ++left;
            --right;
        }
    }

    /**
     * 交换数组两个下标对应的值
     *
     * @param nums 数组
     * @param i    下标i
     * @param j    下标j
     */
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


}