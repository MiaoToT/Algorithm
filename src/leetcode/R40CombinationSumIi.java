package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 应杰
 * @date 2021/11/29
 * @description 40.组合总和II https://leetcode-cn.com/problems/combination-sum-ii/
 */
public final class R40CombinationSumIi {

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new LinkedList<>();
        Arrays.sort(candidates);
        combination(result, new LinkedList<>(), 0, target, candidates);
        return result;
    }

    /**
     * 搜索回溯
     *
     * @param result     最终结果
     * @param tempList   当前结合的列表
     * @param index      当前下标
     * @param target     目标值(根据结合而变小)
     * @param candidates 需要结合的列表
     */
    private static void combination(List<List<Integer>> result, List<Integer> tempList, int index, int target, int[] candidates) {
        if (target == 0) {
            result.add(new LinkedList<>(tempList));
            return;
        }
        for (int i = index; i < candidates.length && target > 0; ++i) {
            // 去重比如1123，凑6，现在是第一个1，123，当到第二个1的时候还是有123，i和i-1的值相等可以直接过滤，i>index能保证不把同一组数据中相同的数据过滤掉
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }
            tempList.add(candidates[i]);
            combination(result, tempList, i + 1, target - candidates[i], candidates);
            tempList.remove(tempList.size() - 1);
        }
    }

}