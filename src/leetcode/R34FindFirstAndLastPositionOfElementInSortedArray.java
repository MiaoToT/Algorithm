package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/24
 * @description 34.在排序数组中查找元素的第一个和最后一个位置 https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/submissions/
 */
public final class R34FindFirstAndLastPositionOfElementInSortedArray {

    /**
     * 二分查找：用boolean做标记，可以对double使用，因为不是+1
     *
     * @param nums   数组
     * @param target 目标值
     * @return 对应的下标区间，左右都包括
     */
    public static int[] searchRange1(int[] nums, int target) {
        // 先找 target 第一次出现的位置
        int leftIndex = search1(nums, target, false);
        // 再找 比 target 大一点点的值第一次出现的位置
        int rightIndex = search1(nums, target, true) - 1;
        // 这里判断下标是否符合要求，并且左下标一定要等于target
        // 也可以把leftIndex > rightIndex改成leftIndex == nums.length，因为这也是left未找到对应target位置时返回的值
        if (leftIndex > rightIndex || nums[leftIndex] != target) {
            return new int[]{-1, -1};
        }
        return new int[]{leftIndex, rightIndex};
    }

    /**
     * 查找 target 第一次出现的位置
     *
     * @param nums            数组
     * @param target          target
     * @param isBigThanTarget 查找情况，true为查找比target大一点点的值，false为正常查找target
     * @return 最终左下标位置，也就是要么是数组长度，要么是找到的第一次出现的位置
     */
    private static int search1(int[] nums, int target, boolean isBigThanTarget) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            // 分为正常的二分查找(isBigThanTarget为false)，或者是查找比target大一点点的值(isBigThanTarget为true)
            boolean normal = !isBigThanTarget && nums[mid] >= target;
            boolean findBigger = nums[mid] > target;
            if (findBigger || normal) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 二分查找偷懒思路：只适用于int，如果是double可能就不能用了
     *
     * @param nums   数组
     * @param target 目标值
     * @return 对应的下标区间，左右都包括
     */
    public static int[] searchRange2(int[] nums, int target) {
        // 先找 target 第一次出现的位置
        int leftIndex = search2(nums, target);
        // 再找 比 target 大一点点的值第一次出现的位置
        int rightIndex = search2(nums, target + 1) - 1;
        // 如果 left 下标越界了或者 left 对应的值不是 target 说明没找到对应的值
        if (leftIndex > rightIndex || nums[leftIndex] != target) {
            return new int[]{-1, -1};
        }
        return new int[]{leftIndex, rightIndex};
    }

    /**
     * 查找 target 第一次出现的位置
     *
     * @param nums   数组
     * @param target target
     * @return 最终左下标位置，也就是要么是数组长度，要么是找到的第一次出现的位置
     */
    private static int search2(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            // 碰到相同的也要往左边靠是为了找到target最开始的位置，比如8888，target也为8，这样可以找到最开始位置为0
            if (nums[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

}