package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/23
 * @description 33.搜索旋转排序数组 https://leetcode-cn.com/problems/search-in-rotated-sorted-array/
 */
public final class R33SearchInRotatedSortedArray {

    /**
     * 二分查找思路：旋转的数组从中间分开，一定有一部分是有序的，
     * 看target在哪一部分，如果在有序的部分则进行二分查找，在无序的部分则顺序查找
     *
     * @param nums   数组
     * @param target 目标值
     * @return 目标值下标
     */
    public static int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[0] <= nums[mid]) {
                // 左半边有序，[0~mid]之间
                if (nums[0] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // 右半边有序，[mid+1~length-1]
                if (nums[mid] < target && target <= nums[nums.length - 1]) {
                    left = mid + 1;
                } else {
                    // 这里去无序查就，到时候二分还是一半是有序的，一半是无序的，如果一直走的是无序就相当于是顺序查找了
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

}