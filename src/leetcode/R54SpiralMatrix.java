package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/12/24
 * @description 54.螺旋矩阵 https://leetcode-cn.com/problems/spiral-matrix/
 */
public final class R54SpiralMatrix {

    /**
     * 行列输出，不进行转向操作
     *
     * @param matrix 矩阵
     * @return 螺旋输出数字
     */
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int bottom = matrix.length - 1;
        /*
         * 1 2 3
         * 4 5 6
         * 7 8 9
         * 比如像这样的，首先输出123，因为3被输出了，所以接下来输出69，然后倒着输出87，最后往上4，之后5
         */
        while (left <= right && top <= bottom) {
            // 输出row行所有元素。(刚开始的右走)
            for (int col = left; col <= right; ++col) {
                result.add(matrix[top][col]);
            }
            // 输出col列所有元素，这里+1，是因为行和列相交了一个元素。(第一次转向往下走)
            for (int row = top + 1; row <= bottom; ++row) {
                result.add(matrix[row][right]);
            }
            if (left < right && top < bottom) {
                // 第二次转向往左走
                for (int col = right - 1; col >= left; --col) {
                    result.add(matrix[bottom][col]);
                }
                // 第三次转向往上走
                for (int row = bottom - 1; row > top; --row) {
                    result.add(matrix[row][left]);
                }
            }
            ++left;
            --right;
            ++top;
            --bottom;
        }
        return result;
    }

}