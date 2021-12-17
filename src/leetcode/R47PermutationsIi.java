package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/12/17
 * @description 47.全排列II https://leetcode-cn.com/problems/permutations-ii/
 */
public final class R47PermutationsIi {

    public static List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        // 维护访问过的数
        boolean[] visitedIndex = new boolean[nums.length];
        // 回溯
        backtrack(nums, result, visitedIndex, new ArrayList<>(), 0);
        return result;
    }

    /**
     * 搜索回溯
     *
     * @param nums         参数数组
     * @param result       最终结果
     * @param visitedIndex 访问过的下标
     * @param solutionList 一个解
     * @param index        控制最外层循环，也可以理解为找index位置上的值
     */
    private static void backtrack(int[] nums, List<List<Integer>> result,
                                  boolean[] visitedIndex, List<Integer> solutionList,
                                  int index) {
        // 先确认结束条件
        if (solutionList.size() == nums.length) {
            result.add(new ArrayList<>(solutionList));
            return;
        }
        for (int i = 0; i < nums.length; ++i) {
            // !visitedIndex[i-1]保证index位置不会填入重复数字，因为数组被排序过，所以填入的数字一定是从左往右第一个未被填过的数字。
            // 比如111，第一个1插入，根据条件第一个1插入了所以第二个1能插入，同理第三个1插入
            // 此时发生回退，第三个1去除，第二个1去除，开始循环，准备插入第三个1，因为第二个1未被插入所以插入第三个1时被跳过
            // 发生回退，第一个1去除，开始循环，由于第一个1没有插入，所以导致第二个1只能跳过，同理第三个，以此保证相同位置不会插入重复数据
            // 这里可以换个思路，用一个值记录上一次的值，者里做一下比较如果相同跳过即可。比如preNum==nums[i]
            boolean isVisited = visitedIndex[i] || (i > 0 && nums[i] == nums[i - 1] && !visitedIndex[i - 1]);
            if (isVisited) {
                continue;
            }
            // 遍历未访问过的数字
            solutionList.add(nums[i]);
            visitedIndex[i] = true;
            backtrack(nums, result, visitedIndex, solutionList, index + 1);
            visitedIndex[i] = false;
            solutionList.remove(index);
        }
    }

}