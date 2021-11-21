package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/21
 * @description 27.移除元素 https://leetcode-cn.com/problems/remove-element/
 */
public final class R27RemoveElement {

    /**
     * 双指针思路：同R26题
     *
     * @param nums 数组
     * @param val  所给元素
     * @return 长度
     */
    public static int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        int left = 0;
        for (int right = 0; right < nums.length; ++right) {
            if (nums[right] != val) {
                nums[left] = nums[right];
                ++left;
            }
        }
        return left;
    }

}