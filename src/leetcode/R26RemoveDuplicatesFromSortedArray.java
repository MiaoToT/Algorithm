package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/21
 * @description 26.删除有序数组中的重复项 https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/
 */
public final class R26RemoveDuplicatesFromSortedArray {

    /**
     * 双指针思路：一个指向当前需要设置的值，一个去遍历数组，碰到当前与上一个不相同的值，则更新第一个指针的值并移动第一个指针到下一个位置
     *
     * @param nums 有序数组
     * @return 数组最终无重复的长度
     */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int left = 1;
        int right = 1;
        while (right < nums.length) {
            if (nums[right] != nums[right - 1]) {
                nums[left] = nums[right];
                ++left;
            }
            ++right;
        }
        return left;
    }

}