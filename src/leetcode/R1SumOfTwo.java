package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 喵粮都输光了
 * @date 2021/10/23
 * @description 1.两数之和 https://leetcode-cn.com/problems/two-sum/
 */
public final class R1SumOfTwo {

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashMap = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; ++i) {
            int calculateValue = target - nums[i];
            // 计算一个值存入map，如果发现另一个target计算出来的在map中，就说明两边加起来就是target，也就得出了答案
            // 这里用 hashMap 代替内层 for 循环查找是否存在另一个值，时间复杂度从O(n)降低至O(1)，但是空间复杂度从O(1)提升至O(n)
            if (hashMap.containsKey(calculateValue)) {
                return new int[]{hashMap.get(calculateValue), i};
            }
            hashMap.put(nums[i], i);
        }
        return new int[0];
    }

}
