package leetcode;

import java.util.Arrays;

/**
 * @author 喵粮都输光了
 * @date 2021/10/20
 * @description 最接近的三数之和 https://leetcode-cn.com/problems/3sum-closest/
 */
public final class R16SumOfThreeClosest {

    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        // 防止溢出
        int result = (int) Math.pow(10, 3);
        for (int i = 0; i < nums.length - 2; ++i) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                // 跳过重复的数字
                continue;
            }
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int tempResult = nums[i] + nums[j] + nums[k];
                if (tempResult == target) {
                    return tempResult;
                } else if (Math.abs(tempResult - target) < Math.abs(result - target)) {
                    result = tempResult;
                } else {
                    if (tempResult > target) {
                        --k;
                        while (j < k && nums[k] == nums[k + 1]) {
                            --k;
                        }
                    } else {
                        ++j;
                        while (j < k && nums[j] == nums[j - 1]) {
                            ++j;
                        }
                    }
                }
            }
        }
        return result;
    }

}
