package leetcode;

import leetcode.auxiliary.ListNode;

/**
 * @author 喵粮都输光了
 * @date 2021/10/23
 * @description 2.两数相加 https://leetcode-cn.com/problems/add-two-numbers/
 */
public final class R2AddTwoNumbers {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = null, current = null;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int n1 = (l1 != null) ? l1.val : 0;
            int n2 = (l2 != null) ? l2.val : 0;
            int sum = n1 + n2 + carry;
            if (root == null) {
                root = current = new ListNode(sum % 10);
            } else {
                current.next = new ListNode(sum % 10);
                current = current.next;
            }
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            current.next = new ListNode(carry);
        }
        return root;
    }

}
