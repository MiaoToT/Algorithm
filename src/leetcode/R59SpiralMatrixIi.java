package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/27
 * @description 59.螺旋矩阵II https://leetcode-cn.com/problems/spiral-matrix-ii/
 */
public final class R59SpiralMatrixIi {

    public static int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int currentDirection = 0;
        int row = 0;
        int col = 0;
        int i = 1;
        while (i <= n * n) {
            result[row][col] = i;
            ++i;
            int nextRow = row + directions[currentDirection][0];
            int nextCol = col + directions[currentDirection][1];
            if (nextRow < 0 || nextRow == n || nextCol < 0 || nextCol == n || result[nextRow][nextCol] != 0) {
                currentDirection = (currentDirection + 1) % 4;
            }
            row += directions[currentDirection][0];
            col += directions[currentDirection][1];
        }
        return result;
    }

}