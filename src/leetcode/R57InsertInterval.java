package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/12/26
 * @description 57.插入区间 https://leetcode-cn.com/problems/insert-interval/
 */
public final class R57InsertInterval {

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        // 标记需要插入的区间是否插入数组了
        boolean flag = true;
        for (int[] interval : intervals) {
            if (interval[0] > newInterval[1]) {
                if (flag) {
                    // 如果标记为true说明需要插入的区间还没有插入，把需要插入的区间先插入
                    result.add(newInterval);
                    flag = false;
                }
                // 要插入的区间在当前区间的左边，需要插入的区间插入了，然后把当前区间插入
                result.add(interval);
            } else if (interval[1] < newInterval[0]) {
                // 不需要合并
                result.add(interval);
            } else {
                // 需要合并
                newInterval[0] = Math.min(interval[0], newInterval[0]);
                newInterval[1] = Math.max(interval[1], newInterval[1]);
            }
        }
        if (flag) {
            result.add(newInterval);
        }
        return result.toArray(new int[result.size()][]);
    }

}