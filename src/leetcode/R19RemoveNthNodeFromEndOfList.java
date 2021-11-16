package leetcode;

import leetcode.auxiliary.ListNode;

/**
 * @author 喵粮都输光了
 * @date 2021/10/23
 * @description 19. 删除链表的倒数第 N 个结点 https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 */
public final class R19RemoveNthNodeFromEndOfList {

    /**
     * 方法1：快慢指针
     *
     * @param head 头节点
     * @param n    要删除的位置
     * @return ListNode
     */
    public static ListNode removeNthFromEnd1(ListNode head, int n) {
        // 哑节点，让头节点和其他节点一样可以通常处理，不需要特殊判断
        ListNode dummy = new ListNode(0, head);
        // 这里指向哑节点，那么和head的差距就是n+1，翻遍删除节点
        ListNode preNode = dummy;
        ListNode postNode = head;
        for (int i = 0; i < n; ++i) {
            postNode = postNode.next;
        }
        while (postNode != null) {
            preNode = preNode.next;
            postNode = postNode.next;
        }
        preNode.next = preNode.next.next;
        // 这里不能返回head，因为head可能被删除，例如：节点只有1，删除1
        return dummy.next;
    }

    /**
     * 方法2：回溯算法，思路就是用栈，遍历到末尾然后回退一个值+1直到n
     *
     * @param head 头节点
     * @param n    要删除的位置
     * @return ListNode
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        int traverse = traverse(head, n);
        if (traverse == n) {
            return head.next;
        }
        return head;
    }

    private static int traverse(ListNode node, int n) {
        if (node == null) {
            return 0;
        }
        int num = traverse(node.next, n);
        if (num == n) {
            node.next = node.next.next;
        }
        return num + 1;
    }

}
