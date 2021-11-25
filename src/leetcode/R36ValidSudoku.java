package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/25
 * @description 36.有效的数独 https://leetcode-cn.com/problems/valid-sudoku/
 */
public final class R36ValidSudoku {

    public static boolean isValidSudoku(char[][] board) {
        int maxNum = 9;
        // 某行上，数字的个数，如rows[0][0] = 1则代表第一行数字1的个数为1个
        int[][] rows = new int[maxNum][maxNum];
        // 某列上，数字的个数
        int[][] columns = new int[maxNum][maxNum];
        // 某九宫格上，数字的个数
        int[][][] subBoxes = new int[3][3][maxNum];
        // 行
        for (int i = 0; i < maxNum; ++i) {
            // 列
            for (int j = 0; j < maxNum; ++j) {
                char currentNum = board[i][j];
                if (currentNum != '.') {
                    // 计算数字的下标位置
                    int index = currentNum - '0' - 1;
                    // 行上该数字个数增1
                    ++rows[i][index];
                    // 列上该数字个数增1
                    ++columns[j][index];
                    // 第几个九宫格内的该数字个数增1
                    ++subBoxes[i / 3][j / 3][index];
                    // 如果有一个重复了，也就是对应的数字个数大于1，则直接返回失败
                    if (rows[i][index] > 1 || columns[j][index] > 1 || subBoxes[i / 3][j / 3][index] > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}