package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/10/10
 * @description 15.三数之和 https://leetcode-cn.com/problems/3sum/
 */
public final class R15SumOfThree {

    public static List<List<Integer>> threeSum(int[] nums) {
        // 一层大循环，二层和三层合并在一起，二层从左开始，三层从右开始。
        Arrays.sort(nums);
        List<List<Integer>> result = new LinkedList<>();
        for (int i = 0; i < nums.length - 2 && nums[i] <= 0; ++i) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                // 跳过重复的数字
                continue;
            }
            // 二层永远从左边开始，比第一层大一位置
            int j = i + 1;
            // 三层永远从最右边开始
            int z = nums.length - 1;
            while (j < z) {
                if (nums[i] + nums[j] + nums[z] == 0) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[z]));
                    // 这里的比较一定要注意，是当前值和下一个值
                    while(j < z && nums[j] == nums[j + 1]) {
                        ++j;
                    }
                    ++j;
                    while(j < z && nums[z] == nums[z - 1]) {
                        --z;
                    }
                } else if (nums[i] + nums[j] + nums[z] > 0) {
                    --z;
                } else {
                    ++j;
                }
            }
        }
        return result;
    }

}
