package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/12/24
 * @description 56.合并区间 https://leetcode-cn.com/problems/merge-intervals/
 */
public final class R56MergeIntervals {

    public static int[][] merge(int[][] intervals) {
        List<int[]> result = new ArrayList<>();
        Arrays.sort(intervals, Comparator.comparing(item -> item[0]));
        for (int[] interval : intervals) {
            int leftPoint = interval[0];
            int rightPoint = interval[1];
            if (result.size() == 0 || result.get(result.size() - 1)[1] < rightPoint) {
                result.add(new int[]{leftPoint, rightPoint});
            } else {
                result.get(result.size() - 1)[1] = Math.max(result.get(result.size() - 1)[1], rightPoint);
            }
        }
        return result.toArray(new int[result.size()][]);
    }

}