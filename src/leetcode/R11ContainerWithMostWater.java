package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/3
 * @description 11.盛最多水的容器 https://leetcode-cn.com/problems/container-with-most-water/
 */
public final class R11ContainerWithMostWater {

    public static int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int result = 0;
        while (left < right) {
            result = Math.max(result, Math.min(height[left], height[right]) * (right - left));
            if (height[left] < height[right]) {
                ++left;
            } else {
                --right;
            }
        }
        return result;
    }

}
