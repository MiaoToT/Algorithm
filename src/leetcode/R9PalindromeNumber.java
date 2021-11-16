package leetcode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/3
 * @description 9.回文数 https://leetcode-cn.com/problems/palindrome-number/
 */
public final class R9PalindromeNumber {

    public static boolean isPalindrome(int x) {
        // x必须大于0，如果个位为0，只有第一位为0，符合的只有0
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int revertedNumber = 0;
        // 这里是取一半进行比较，比如1234321，只有revertedNumber为4321的时候才会比x的123大。可以即此判断达到一半
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }
        // 例如1221，可以直接比较12和12，如果是12321就需要比较12和123，除去最后一位即可比较12和12
        return x == revertedNumber || x == revertedNumber / 10;
    }

}
