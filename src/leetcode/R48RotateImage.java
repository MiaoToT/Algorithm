package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/19
 * @description 48.旋转图像 https://leetcode-cn.com/problems/rotate-image/
 */
public final class R48RotateImage {

    /**
     * 旋转：假设4✖4，(0,1)<-(1,3)<-(3,2)<-(2,0)<-(0,1)
     * 可见四次一个循环，且因为是九十度旋转，由此可以推导出公式
     * 顺时针：(row,col)->(n-1-col,row)->(n-1-row,n-1-col)->(col,n-1-row)->(row,col)
     * 逆时针：(row,col)->(col,n-1-row)->(n-1-row,n-1-col)->(n-1-col,row)->(row,col)
     * <p>
     * 枚举的位置：枚举第一行(除了最后一个)就可以解决最外面一圈，枚举第二行(除了第一个和最后两个)就可以解决从外往内第二圈的值
     * 而且发现枚举(1,0)可以得到(0,2)，所以可以枚举1/4就相当于如上的枚举了，但是这里还要分边长是奇还是偶，偶数如上，奇数的话行或列其中一个必须要多枚举一点才可以枚举完所有的值，
     * 因为偶数/2=(偶数+1)/2，所以奇偶可以并在一块处理
     *
     * @param matrix 二维矩阵
     */
    public static void rotate1(int[][] matrix) {
        int n = matrix.length;
        int half = 2;
        for (int i = 0; i < (n + 1) / half; ++i) {
            for (int j = 0; j < n / half; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = temp;
            }
        }
    }

    /**
     * 反转代替旋转：先水平翻转，然后主对角线翻转即可获得顺时针九十度旋转
     *
     * @param matrix 二维矩阵
     */
    public static void rotate2(int[][] matrix) {
        int n = matrix.length;
        int half = 2;
        // 水平翻转
        for (int i = 0; i < n / half; ++i) {
            for (int j = 0; j < n; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - i - 1][j];
                matrix[n - i - 1][j] = temp;
            }
        }
        // 主对角线翻转，因为是主对角线\，所以列一定小于行
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

}