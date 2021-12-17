package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/12/9
 * @description 46.全排列 https://leetcode-cn.com/problems/permutations/
 */
public final class R46Permutations {

    /**
     * 回溯算法：通过交换来访问未访问过的数
     *
     * @param nums 数组
     * @return 全排列
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // 初始化一个解
        List<Integer> solutionList = new ArrayList<>();
        for (int num : nums) {
            solutionList.add(num);
        }
        backtrack(nums.length, solutionList, result, 0);
        return result;
    }

    /**
     * 回溯法交换重置状态
     *
     * @param length       数组长度
     * @param solutionList 一个解
     * @param result       结果
     * @param currentIndex 当前下标
     */
    public static void backtrack(int length, List<Integer> solutionList,
                                 List<List<Integer>> result, int currentIndex) {
        // 先确认结束条件
        if (currentIndex == length) {
            result.add(new ArrayList<>(solutionList));
            return;
        }
        // [|1,2,3] -交换1,1-> [1|2,3] -交换2,2-> [1,2|3] -交换3,3-> [1,2,3|存入] -回退3,3-> [1,2|3] -回退2,2->
        // [1|2,3] -交换2,3-> [1,3|2] -交换2,2-> [1,3,2|存入] -回退2,2-> [1,3|2] -回退3,2-> [1|2,3] -回退1,1->
        // [|1,2,3] -交换1,2-> [2|1,3] -交换1,1-> [2,1|3] -交换3,3-> [2,1,3|存入] ....依此类推
        for (int i = currentIndex; i < length; ++i) {
            Collections.swap(solutionList, currentIndex, i);
            backtrack(length, solutionList, result, currentIndex + 1);
            Collections.swap(solutionList, currentIndex, i);
        }
    }

}