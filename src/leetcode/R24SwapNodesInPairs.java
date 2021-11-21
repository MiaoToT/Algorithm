package leetcode;

import leetcode.auxiliary.ListNode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/17
 * @description 24.两两交换链表中的节点 https://leetcode-cn.com/problems/swap-nodes-in-pairs/
 */
public final class R24SwapNodesInPairs {

    /**
     * 迭代
     *
     * @param head 头节点
     * @return 头节点
     */
    public static ListNode swapPairs1(ListNode head) {
        ListNode root = new ListNode(0, head);
        ListNode temp = root;
        while (temp.next != null && temp.next.next != null) {
            ListNode first = temp.next;
            ListNode second = temp.next.next;
            first.next = second.next;
            second.next = first;
            temp.next = second;
            temp = first;
        }
        return root.next;
    }

    /**
     * 递归
     *
     * @param head 头节点
     * @return 头节点
     */
    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 第二个节点
        ListNode secondHead = head.next;
        // 其余的节点，分别进行两两交换
        head.next = swapPairs2(secondHead.next);
        // 交换第一个节点和第二个节点的位置
        secondHead.next = head;
        return secondHead;
    }

}