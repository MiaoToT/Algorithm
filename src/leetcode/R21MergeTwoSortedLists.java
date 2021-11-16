package leetcode;

import leetcode.auxiliary.ListNode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/5
 * @description 21.合并两个有序链表 https://leetcode-cn.com/problems/merge-two-sorted-lists/
 */
public final class R21MergeTwoSortedLists {

    /**
     * 迭代
     *
     * @param l1 链表1
     * @param l2 链表2
     * @return ListNode
     */
    public static ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        ListNode root = new ListNode();
        ListNode current = root;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        current.next = (l1 == null) ? l2 : l1;
        return root.next;
    }

    /**
     * 递归
     *
     * @param l1 链表1
     * @param l2 链表2
     * @return ListNode
     */
    public static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists2(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists2(l1, l2.next);
            return l2;
        }
    }

}
