package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/22
 * @description 52.N皇后II https://leetcode-cn.com/problems/n-queens-ii/
 */
public final class R52QueensIi {

    public static int totalQueens(int n) {
        return recursion(n, 0, 0, 0, 0);
    }

    /**
     * 递归计算解的个数，具体参考R51
     *
     * @param row       当前行
     * @param col       列上是否有皇后，位运算存储(最左列对应二进制最低位，0可以放，1不能放)
     * @param slash     斜线上是否有皇后，位运算存储(最左列对应二进制最低位，0可以放，1不能放)
     * @param backslash 反斜线上是否有皇后，位运算存储(最左列对应二进制最低位，0可以放，1不能放)
     */
    private static int recursion(int n, int row, int col, int slash, int backslash) {
        if (row == n) {
            return 1;
        }
        int count = 0;
        int availablePositions = ((1 << n) - 1) & (~(col | slash | backslash));
        while (availablePositions != 0) {
            int position = availablePositions & (-availablePositions);
            availablePositions = availablePositions & (availablePositions - 1);
            count += recursion(n, row + 1, col | position, (slash | position) >> 1, (backslash | position) << 1);
        }
        return count;
    }


}