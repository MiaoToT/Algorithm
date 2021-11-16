package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/10/23
 * @description 18.四数之和 https://leetcode-cn.com/problems/4sum/
 */
public final class R18SumOfFour {

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new LinkedList<>();
        int preListSize = 4;
        if (nums == null || nums.length < preListSize) {
            return result;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 下面两个if不必要，只是减少循环
            if (nums[i] + nums[i + 1] > target - nums[i + 2] - nums[i + 3]) {
                break;
            }
            if (nums[i] + nums[nums.length - 3] < target - nums[nums.length - 2] - nums[nums.length - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                // 下面两个if不必要，只是减少循环
                if (nums[i] + nums[j] > target - nums[j + 1] - nums[j + 2]) {
                    break;
                }
                if (nums[i] + nums[j] < target - nums[nums.length - 2] - nums[nums.length - 1]) {
                    continue;
                }
                int left = j + 1;
                int right = nums.length - 1;
                while (left < right) {
                    // nums[i] + nums[j] + nums[left] + nums[right] == target 改成下面这样是为了防止溢出
                    if (nums[i] + nums[j] == target - nums[left] - nums[right]) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        // 这里的比较一定要注意，是当前值和下一个值
                        while (left < right && nums[left] == nums[left + 1]) {
                            ++left;
                        }
                        ++left;
                        while (left < right && nums[right] == nums[right - 1]) {
                            --right;
                        }
                    } else if (nums[i] + nums[j] < target - nums[left] - nums[right]) {
                        ++left;
                    } else {
                        --right;
                    }
                }
            }
        }
        return result;
    }

}
