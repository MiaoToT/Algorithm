package leetcode;

import java.util.*;

/**
 * @author 喵粮都输光了
 * @date 2021/12/9
 * @description 46.全排列 https://leetcode-cn.com/problems/permutations/
 */
public final class R46Permutations {

    /**
     * 回溯算法1：通过状态来维护访问过的数
     *
     * @param nums 数组
     * @return 全排列
     */
    public static List<List<Integer>> permute1(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // 维护访问过的数
        Map<Integer, Boolean> visitedMap = new HashMap<>(nums.length);
        for (int num : nums) {
            visitedMap.put(num, false);
        }
        // 回溯
        backtrack1(nums, result, visitedMap, new LinkedList<>());
        return result;
    }

    /**
     * 回溯法1Map状态重置
     *
     * @param nums         数组
     * @param result       结果
     * @param visitedMap   访问过的map
     * @param solutionList 一个解
     */
    public static void backtrack1(int[] nums, List<List<Integer>> result,
                                  Map<Integer, Boolean> visitedMap,
                                  LinkedList<Integer> solutionList) {
        // 先确认结束条件
        if (solutionList.size() == nums.length) {
            result.add(new ArrayList<>(solutionList));
            return;
        }
        // 循环数组，例如123(解)，退格3发现后面没数字了，继续退格2，发现后面有个3，
        // 然后就是退格2，存入3变成13，然后进入递归131，1访问过了所以132(解),3访问过了所以不是，依次类推
        for (int num : nums) {
            // 遍历未访问过的数字
            if (!visitedMap.get(num)) {
                solutionList.add(num);
                visitedMap.put(num, true);
                backtrack1(nums, result, visitedMap, solutionList);
                solutionList.removeLast();
                visitedMap.put(num, false);
            }
        }
    }

    /**
     * 回溯算法2：通过交换来访问未访问过的数
     *
     * @param nums 数组
     * @return 全排列
     */
    public static List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // 初始化一个解
        List<Integer> solutionList = new ArrayList<>();
        for (int num : nums) {
            solutionList.add(num);
        }
        backtrack2(nums.length, solutionList, result, 0);
        return result;
    }

    /**
     * 回溯法2交换重置状态
     *
     * @param length       数组长度
     * @param solutionList 一个解
     * @param result       结果
     * @param currentIndex 当前下标
     */
    public static void backtrack2(int length, List<Integer> solutionList,
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
            backtrack2(length, solutionList, result, currentIndex + 1);
            Collections.swap(solutionList, currentIndex, i);
        }
    }

}