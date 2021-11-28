package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/11/27
 * @description 39.组合总和 https://leetcode-cn.com/problems/combination-sum/
 */
public final class R39CombinationSum {

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new LinkedList<>();
        List<Integer> tempList = new LinkedList<>();
        combination(result, tempList, 0, target, candidates);
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
        if (index == candidates.length) {
            return;
        }
        if (target == 0) {
            result.add(new LinkedList<>(tempList));
            return;
        }
        // 先用当前相同的值进行计算
        if (target - candidates[index] >= 0) {
            tempList.add(candidates[index]);
            combination(result, tempList, index, target - candidates[index], candidates);
            tempList.remove(tempList.size() - 1);
        }
        // 使用下一个index的值进行组合
        combination(result, tempList, index + 1, target, candidates);
    }

}