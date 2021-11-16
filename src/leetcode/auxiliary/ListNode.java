package leetcode.auxiliary;

/**
 * @author 喵粮都输光了
 * @date 2021/10/23
 * @description 链表节点
 */
public final class ListNode {

    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

}