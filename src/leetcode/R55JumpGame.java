package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/12/24
 * @description 55.跳跃游戏 https://leetcode-cn.com/problems/jump-game/
 */
public final class R55JumpGame {

    public static boolean canJump(int[] nums) {
        // i<=rightMost是为了保证不可达的情况下能及时返回false，例如3,2,1,0,4
        for (int i = 0, rightMost = 0; i < nums.length && i <= rightMost; ++i) {
            // 维护当前下标能跳到的最远下标
            rightMost = Math.max(rightMost, i + nums[i]);
            if (rightMost >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

}