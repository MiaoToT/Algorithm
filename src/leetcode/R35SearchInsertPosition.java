package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/24
 * @description 35.搜索插入位置 https://leetcode-cn.com/problems/search-insert-position/
 */
public final class R35SearchInsertPosition {

    /**
     * 二分查找：查找插入位置，也就是查找target第一次出现的位置(如果存在，哪怕有重复)
     * 如果不存在，则会查找插入位置
     *
     * @param nums   数组
     * @param target 目标值
     * @return 存在返回索引，不存在返回按顺序插入的下标
     */
    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (nums[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

}