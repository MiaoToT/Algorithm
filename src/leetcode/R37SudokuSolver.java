package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/27
 * @description 37.解数独 https://leetcode-cn.com/problems/sudoku-solver/
 */
public final class R37SudokuSolver {

    /**
     * 数独方格最大长度
     */
    private static final int MAX_LEN = 9;
    /**
     * 空格标识符
     */
    private static final char SPACE_SYMBOL = '.';
    /**
     * 某行上，某个数字是否存在(位运算优化)
     */
    private static final int[] ROWS = new int[MAX_LEN];
    /**
     * 某列上，某个数字是否存在(位运算优化)
     */
    private static final int[] COLS = new int[MAX_LEN];
    /**
     * 某个九宫格内，某个数字是否存在，九宫格只有3*3个(位运算优化)
     */
    private static final int[][] CELLS = new int[3][3];

    /**
     * dfs递归，回溯思路：这样会使用R36中的rows、cols和cells
     * 位运算优化：可以将rows、cols和cells压缩成一个数去表示，数组降一维(未优化的具体看R36)
     * 枚举优化：预先处理一部分能够唯一确认的数字
     *
     * @param board 所给数独数据
     */
    public static void solveSudoku(char[][] board) {
        for (int i = 0; i < MAX_LEN; ++i) {
            for (int j = 0; j < MAX_LEN; ++j) {
                // 如果不是空格标识符则将当前位置的数字记录在ROWS、COLS和CELLS中
                if (board[i][j] != SPACE_SYMBOL) {
                    // 把有数字的地方记录到相关信息组中
                    flip(i, j, board[i][j] - '0' - 1);
                }
            }
        }
        // 预先处理一部分能够唯一确认的数字
        while (true) {
            boolean modified = false;
            for (int i = 0; i < MAX_LEN; ++i) {
                for (int j = 0; j < MAX_LEN; ++j) {
                    if (board[i][j] == SPACE_SYMBOL) {
                        // 获取未占位情况，0x1ff代表二进制9个1，mask具体看dfs方法中说明
                        int mask = ~(ROWS[i] | COLS[j] | CELLS[i / 3][j / 3]) & 0x1ff;
                        // mask&(mask-1)代表去除最低位的1，如果去除后为0说明当前值为9个数字里面的最后一个值，当然可以唯一确认
                        if ((mask & (mask - 1)) == 0) {
                            int index = Integer.bitCount(mask - 1);
                            flip(i, j, index);
                            board[i][j] = (char) (index + '0' + 1);
                            modified = true;
                        }
                    }
                }
            }
            // 如果填入了唯一能确认的值，那么可能导致其他地方或许也能够继续，继续循环
            if (!modified) {
                break;
            }
        }
        // 递归处理不能唯一确认的数字
        dfs(board, 0, 0);
    }

    private static boolean dfs(char[][] board, int row, int col) {
        // 如果列达到了末尾，则需要换行，并将列重新置为0
        if (col == MAX_LEN) {
            ++row;
            col = 0;
        }
        // 如果行达到了末尾则说明当前数独解是符合要求的
        if (row == MAX_LEN) {
            return true;
        }
        // 如果当前单元格有值则进行列+1去遍历处理下一个单元格数字
        if (board[row][col] != SPACE_SYMBOL) {
            return dfs(board, row, col + 1);
        }
        // 遍历当前单元格的值在该行、列、九宫格内是否存在
        // 此处0x1ff的意思是(111111111)2=(1FF)16，用二进制从低到高代替boolean数组从左到右的数字占用情况
        // rows、cols和cells代表的是占用状态，他们的二进制(1)代表占用，(0)代表未占用，使用或运算获取所有占用的位置，再通过取反获取未占用值
        // 与0x1ff是为了除去9个值以外的所有1导致的干扰情况
        int mask = ~(ROWS[row] | COLS[col] | CELLS[row / 3][col / 3]) & 0x1ff;
        while (mask != 0) {
            // 获取最低位的1，-mask为mask取反+1，mask与~mask按位与会得到0，
            // 如果~maks+1则会导致最低位的连续的1都变为0，而最低位的0变为1即mask中最低位的1
            int indexMask = mask & (-mask);
            // indexMask得到的值类似于二进制的100000,用这个值-1可以得到011111这样的值，然后数一下有多少个1就可以得到值的下标
            int index = Integer.bitCount(indexMask - 1);
            flip(row, col, index);
            board[row][col] = (char) (index + '0' + 1);
            // 设置当前单元格的值，并以这个值开始进行接下来的值的设置
            if (dfs(board, row, col + 1)) {
                // 如果接下来值都符合要求则返回该值是正确的数独解
                return true;
            }
            // 如果当前值不是数独解，则还原当前值为空格标识符，填入下一个数字继续
            board[row][col] = SPACE_SYMBOL;
            flip(row, col, index);
            // 去除最低位的掩码，例如1010&=(1010-1)为1010&=1001，最终为1000。(简单说就是去除最右边的1，处理下一个数)
            mask &= (mask - 1);
        }
        return false;
    }

    /**
     * 位运算存储数字是否存在，例如1101，代表134存在，2不存在
     *
     * @param row   行
     * @param col   列
     * @param index 需要存入或者删除的数字的下标，例如1就是下标0
     */
    private static void flip(int row, int col, int index) {
        // 如果左边为0，index为2，则为0^=4，也就是000^=100，结果为100
        // 如果要取消掉index为2，也就是100^=(1<<2)，100^=100，结果为0
        // 1 << index 是为了处理index为0的情况，如果左边为0，index也为0，异或则还是0，导致index为0a存储的值不正确
        // 为什么这里只能让下标0~8进来，因为如果用数字的话就是1~9,2^9为512，而二进制9个1的值为511，最后计算mask的时候会溢出出现问题
        ROWS[row] ^= (1 << index);
        COLS[col] ^= (1 << index);
        CELLS[row / 3][col / 3] ^= (1 << index);
    }

}