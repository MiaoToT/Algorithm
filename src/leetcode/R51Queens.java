package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 喵粮都输光了
 * @date 2021/12/22
 * @description 51.N皇后 https://leetcode-cn.com/problems/n-queens/
 */
public final class R51Queens {

    public static List<List<String>> solveQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        // 皇后坐标：下标代表行，存储的数字代表列
        int[] queens = new int[n];
        Arrays.fill(queens, -1);
        recursion(result, queens, 0, 0, 0, 0);
        return result;
    }

    /**
     * 递归计算解
     *
     * @param result    最终解
     * @param queens    皇后坐标
     * @param row       当前行
     * @param col       列上是否有皇后，位运算存储(最左列对应二进制最低位，0可以放，1不能放)
     * @param slash     斜线上是否有皇后，位运算存储(最左列对应二进制最低位，0可以放，1不能放)
     * @param backslash 反斜线上是否有皇后，位运算存储(最左列对应二进制最低位，0可以放，1不能放)
     */
    private static void recursion(List<List<String>> result, int[] queens,
                                  int row, int col, int slash, int backslash) {
        if (row == queens.length) {
            // 输出解
            List<String> board = new ArrayList<>();
            for (int queen : queens) {
                char[] line = new char[queens.length];
                Arrays.fill(line, '.');
                line[queen] = 'Q';
                board.add(new String(line));
            }
            result.add(board);
            return;
        }
        /*
         * ①、(1<<n)-1，代表的二进制为1左移n位，比如3位就是1000，然后-1就成为了111，也就是说获得一个n位全是1的二进制
         * ②、col、slash和backslash按位或可以获得所有的1也就是不能放置皇后的位置，取反计算能放置的位置
         * ③、步骤1和步骤2相与是为了去除多余的位数造成的影响
         */
        int availablePositions = ((1 << queens.length) - 1) & (~(col | slash | backslash));
        // 当空闲位置不为0时代表还可以放置皇后
        while (availablePositions != 0) {
            // 用于获取最低位的1，n&(~n)为0，n&(-n)可以用3举例，
            // 3的二进制为00000011，~3为11111100，-3为~3+1也就是11111101，所以3&(-3)为00000001
            int position = availablePositions & (-availablePositions);
            // 使用完了去掉最低位的1也就是让空余位数减一，也就是在该位置填充皇后
            availablePositions &= (availablePositions - 1);
            // 统计所有1的个数，也就是获得下标的位置
            int column = Integer.bitCount(position - 1);
            // 覆盖上一次改行皇后的下标，所以不需要递归后queens[row]=-1
            queens[row] = column;
            /*
             * ①、每一次循环都有一行获得了皇后，所以行+1
             * ②、slash也就是/方向的斜线，因为行数下移了一个位置，想让这个斜线覆盖到下一行需要把整个斜线往右移动一个格子，但是因为二进制和斜线移动方向是相反的，
             *    所以这里是左移；backslash同理。
             * ③、全部或position的原因是，position的位置放置了皇后，也就是该位置变成了1，所以相应的三个数的二进制在当前位置都需要变成1
             */
            recursion(result, queens, row + 1, col | position, (slash | position) >> 1, (backslash | position) << 1);
            // 这里递归回来不需要将queens[row]设置为-1，因为每次循环都会对queens[row]这个位置进行覆盖
        }
    }

}