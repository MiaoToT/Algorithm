package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/8
 * @description 45. 跳跃游戏II https://leetcode-cn.com/problems/jump-game-ii/
 */
public final class R45JumpGameIi {

    /**
     * 贪心算法：从右往左看，找到一步就能到达终点的数的下标，取与终点距离最远的数的下标(下标最小的)，
     * 然后将这个数作为终点继续往前推，直到找到数组的开始位置。
     *
     * @param nums 数组
     * @return 跳跃最小的下标
     */
    public static int jump1(int[] nums) {
        int steps = 0;
        int pos = nums.length - 1;
        while (pos > 0) {
            for (int i = 0; i < pos; ++i) {
                // 当前位置的下标 + 改下标能跳跃的最大步数 >= 最终下标
                if (i + nums[i] >= pos) {
                    pos = i;
                    ++steps;
                    break;
                }
            }
        }
        return steps;
    }

    /**
     * 贪心算法：从左往右遍历，在当前能跳到的范围内选择一个能跳的最远的位置
     * 例如2 3 1 2 4 2 3，第一个位置2能跳到3和1，算下来的3能跳到4，1能跳到3，那么选则3作为起跳点。
     * 这时候3能跳到1，2和4，1能跳到2，2能跳到4和2，4能跳到2和3，选择其中能跳的最远的位置作为起跳点，直到能跳到最后。
     *
     * @param nums 数组
     * @return 跳跃最小的下标
     */
    public static int jump2(int[] nums) {
        int steps = 0;
        int pos = 0;
        int end = 0;
        // 因为最后必定能到达最后的位置，所以不需要遍历最后的位置
        for (int i = 0; i < nums.length - 1; ++i) {
            // 维护能跳到的最大下标位置
            pos = Math.max(pos, i + nums[i]);
            // end记录某次能跳到的最大下标位置，到达的时候就把end更新为该值，并增加一次步数。例如第一个数的end一定是0，它的最远距离肯定是它的值
            if (i == end) {
                end = pos;
                ++steps;
            }
        }
        return steps;
    }

}