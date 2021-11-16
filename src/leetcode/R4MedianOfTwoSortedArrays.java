package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/10/24
 * @description 4.寻找两个正序数组的中位数 https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
 */
public final class R4MedianOfTwoSortedArrays {

    /**
     * 主要思路：
     * |        左部分        |           右部分          |
     * | A[0],A[1]...,A[i-1] |  A[i], A[i+1],...,A[m-1] |
     * | B[0],B[1]...,B[j-1] |  B[j], B[j+1],...,B[n-1] |
     * 中位数为：数组1+数组2的长度和为奇数时，中位数为(左部分的最大值+右部分的最小值)/2；为偶数时，中位数为左部分最大值
     * 关键点就是如果使得左部分最大值小于右部分最小值，
     * 也就是第一个数组左部分的最大值小于第二数组右部分的最小值并且第二个数组左部分的最大值小于第一数组右部分的最小值
     *
     * @param nums1 数组1
     * @param nums2 数组2
     * @return double
     */
    public static double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            // 这里让num1最小，对num1进行枚举，不会使得j变成负数，保证分割线两侧都有元素
            return findMedianSortedArrays1(nums2, nums1);
        }
        int length1 = nums1.length;
        int length2 = nums2.length;
        // 根据num1的变动而改变num2的指针
        int left = 0;
        int right = length1;
        // 最终取中位数用
        int maxLeft = 0;
        int minRight = 0;
        // num1[0],num1[1]...,num1[i-1] |  num1[i], num1[i+1],...,num1[m-1]
        // num2[0],num2[1]...,num2[j-1] |  num2[j], num2[j+1],...,num2[n-1]
        while (left <= right) {
            // i为第一个数组中间右一个，这样写防止溢出（实际相当于(left + right) / 2）
            int i = left + (right - left) / 2;
            // 这样写可以防止溢出(实际相当于(length1 + length2 + 1) / 2 - i)，j为第二个数组中间右一个(i变大的话，j相应要变小一点))
            // 这里+1是因为刚开始左边的数要比右边的数多一个，如果第一个数组两个数，第二个数组一个数，不+1会导致j的值为0，左部分数为0，右部分数为1
            int j = length1 + (length2 - length1 + 1) / 2 - i;
            // 处理越界值，因为左部分取最大值，所以越界值设置成MIN_VALUE，右部分取最小值，越界值设置长MAX_VALUE，防止产生影响
            int num1MaxLeft = (i == 0 ? Integer.MIN_VALUE : nums1[i - 1]);
            int num1MinRight = (i == length1 ? Integer.MAX_VALUE : nums1[i]);
            int num2MaxLeft = (j == 0 ? Integer.MIN_VALUE : nums2[j - 1]);
            int num2MinRight = (j == length2 ? Integer.MAX_VALUE : nums2[j]);
            // 前部分的最大值总是小于后部分的最小值
            if (num1MaxLeft <= num2MinRight) {
                // 第一个数组的最大值小于等于第二个数组的最小值，尝试将第一个数组的最大值变大点看看
                maxLeft = Math.max(num1MaxLeft, num2MaxLeft);
                minRight = Math.min(num1MinRight, num2MinRight);
                left = i + 1;
            } else {
                // 第一个数组的最大值大于第二个数组的最小值，尝试将第二个数组的最小值变小点看看
                right = i - 1;
            }
        }
        return (length1 + length2) % 2 == 0 ? (maxLeft + minRight) / 2.0 : maxLeft;
    }

    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int totalLength = nums1.length + nums2.length;
        if (totalLength % 2 == 1) {
            return getKthElement(nums1, nums2, totalLength / 2 + 1);
        } else {
            return (getKthElement(nums1, nums2, totalLength / 2) + getKthElement(nums1, nums2, totalLength / 2 + 1)) / 2.0;
        }
    }

    /**
     * 主要思路：分别有长度为4和9的两个数组，取k=(4+9)/2+1，分别对数组1和2求k/2-1的值，
     * 然后将求出的值当作下标，比较两个数组中该下标的值，如果数组1<=数组2，那么直接排除数组1中下标0~该值下表的数，否则排除数组2的，
     * 更新k的值，k=k-k/2，然后在未排除的数上进行上面的循环操作，直到k=1的时候，比较两个数组中未排除下标范围内第一个数，最小的就是中位数
     *
     * @param nums1 数组1
     * @param nums2 数组2
     * @param k     k=1的时候为未排除数的第一个数，否则就是下标
     * @return 中位数
     */
    private static int getKthElement(int[] nums1, int[] nums2, int k) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int index1 = 0;
        int index2 = 0;
        while (true) {
            // 如果越界了，那么只需要返回另一个数组中第k小的元素即可，即k-1取得下标
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }
            // 取 k/2-1，加上index当作排除；越界取数组最后一位
            int newIndex1 = Math.min(index1 + k / 2, length1) - 1;
            int newIndex2 = Math.min(index2 + k / 2, length2) - 1;
            int pivot1 = nums1[newIndex1];
            int pivot2 = nums2[newIndex2];
            if (pivot1 <= pivot2) {
                // 更新k的值为k=k-k/2，这里这么处理是因为newIndex可能没有达到k/2，那么有多少个就排除多少个
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }

}
