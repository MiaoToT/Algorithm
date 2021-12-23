package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/23
 * @description 53.最大子数组和 https://leetcode-cn.com/problems/maximum-subarray/
 */
public final class R53MaximumSubarray {

    /**
     * 贪心算法：若当前指针所指元素之前的和小于0，则丢弃当前元素之前的数列
     *
     * @param nums 数组
     * @return 最大连续和
     */
    public static int maxSubArray1(int[] nums) {
        int maxResult = nums[0];
        int preMax = 0;
        for (int num : nums) {
            // 前面值的和与当前值的和如果比当前值小那肯定就代表前面的和是负数的，舍弃只要当前值
            preMax = Math.max(preMax + num, num);
            maxResult = Math.max(maxResult, preMax);
        }
        return maxResult;
    }

    /**
     * 分治(线段树)，核心思想是如果把一个数组[left,right]拆成两段[left,middle](称为左分区),[middle+1,right](称为右分区)，也就是分治。
     * 那么解有三种情况：①、最大子序列和在左分区中，②、最大子序列和在右分区中，③、最大子序列和横跨了左分区和右分区，也就是一定包含middle和middle+1。
     * 为此我们需要维护三个变量，左分区最大和、右分区最大和、左分区以middle为终点的最大和+右分区以middle+1为终点的最大和，而其结果就是这三个变量中最大的和。
     * <p>
     * 维护变量：
     *
     * @param nums 数组
     * @return 最大连续和
     */
    public static int maxSubArray2(int[] nums) {
        return recursionMaxSubArray(nums, 0, nums.length - 1).maxSum;
    }

    private static Status recursionMaxSubArray(int[] nums, int left, int right) {
        // 分治，左右的边界相等说明到了最里层
        if (left == right) {
            return new Status(nums[left], nums[left], nums[left], nums[left]);
        }
        int middle = left + (right - left) >> 1;
        Status leftPart = recursionMaxSubArray(nums, left, middle);
        Status rightPart = recursionMaxSubArray(nums, middle + 1, right);
        // 两个分区合并
        return new Status(
                // 区间的总和就是两个分区的区间的总和相加
                leftPart.partSum + rightPart.partSum,
                // 看情况是①还是③
                Math.max(leftPart.leftSum, leftPart.partSum + rightPart.leftSum),
                // 看情况是②还是③
                Math.max(rightPart.rightSum, rightPart.partSum + leftPart.rightSum),
                // 比较左分区最大和右分区最大还有横跨分区的最大，取最大的就是最大连续和
                Math.max(Math.max(leftPart.maxSum, rightPart.maxSum), leftPart.rightSum + rightPart.leftSum)
        );
    }

    private static class Status {

        /**
         * [left,middle]中以left为起始的最大值
         */
        public int leftSum;
        /**
         * [middle+1,right]中以right为终点的最大值
         */
        public int rightSum;
        /**
         * [left,right]中最大连续和
         */
        public int maxSum;
        /**
         * [left,right]区间总和
         */
        public int partSum;

        public Status(int leftSum, int rightSum, int maxSum, int partSum) {
            this.leftSum = leftSum;
            this.rightSum = rightSum;
            this.maxSum = maxSum;
            this.partSum = partSum;
        }
    }

}