package leetcode;

import leetcode.auxiliary.ListNode;

/**
 * @author 喵粮都输光了
 * @date 2021/11/21
 * @description 25.K个一组翻转链表 https://leetcode-cn.com/problems/reverse-nodes-in-k-group/
 */
public final class R25ReverseNodesInGroup {

    /**
     * 每k个节点一组进行翻转，如果不存在k个节点则将节点保持原有顺序。
     *
     * @param head 头节点
     * @param k    一组节点个数
     * @return 头节点
     */
    public static ListNode reverseGroup(ListNode head, int k) {
        ListNode root = new ListNode(0, head);
        ListNode pre = root;
        while (head != null) {
            ListNode tail = pre;
            for (int i = 0; i < k; ++i) {
                tail = tail.next;
                if (tail == null) {
                    return root.next;
                }
            }
            // 其他节点
            ListNode otherNode = tail.next;
            // 反转返回一个头节点和一个尾节点
            ListNode[] reverseResult = reverse(head, tail);
            head = reverseResult[0];
            tail = reverseResult[1];
            // 新子链表重新连接到原链表中
            pre.next = head;
            tail.next = otherNode;
            // 跳过k个节点进行下一波操作
            pre = tail;
            head = tail.next;
        }
        return root.next;
    }

    /**
     * 反转思路：本来是head->1->2->3->tail->other；反转成为other<-head<-1<-2<-3<-tail
     * 反转head到tail之间的元素(包括head和tail)，最后返回新的head和tail
     *
     * @param head 原head
     * @param tail 原tail
     * @return 新head(ListNode)和新tail(ListNode.next)，
     */
    private static ListNode[] reverse(ListNode head, ListNode tail) {
        // 记录上一个节点
        ListNode pre = tail.next;
        // 当前需要操作的节点
        ListNode current = head;
        // 上一个节点不是tail就可以一直循环操作，即other不是tail
        while (pre != tail) {
            // 把当前节点指向上一个节点，上一个节点变成当前节点，当前节点跳到下一个节点
            ListNode next = current.next;
            current.next = pre;
            pre = current;
            current = next;
        }
        return new ListNode[]{tail, head};
    }

}