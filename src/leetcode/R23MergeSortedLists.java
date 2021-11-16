package leetcode;

import leetcode.auxiliary.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author 应杰
 * @date 2021/11/16
 * @description 23.合并K个升序链表 https://leetcode-cn.com/problems/merge-k-sorted-lists/
 */
public final class R23MergeSortedLists {

    /**
     * 顺序合并：就是遍历数组，用前两个合并的结果合并第三个得到的结果合并第四个直到最后
     *
     * @param lists 数组
     * @return ListNode
     */
    public static ListNode mergeLists1(ListNode[] lists) {
        ListNode result = null;
        for (ListNode list : lists) {
            result = R21MergeTwoSortedLists.mergeTwoLists1(result, list);
        }
        return result;
    }

    /**
     * 分治合并：两两合并
     *
     * @param lists 数组
     * @return ListNode
     */
    public static ListNode mergeLists2(ListNode[] lists) {
        return merge(lists, 0, lists.length - 1);
    }

    /**
     * 两两合并，不断从中间划开，然后回溯合并
     *
     * @param lists 数组
     * @param start 开始下标
     * @param end   末尾下标
     * @return ListNode
     */
    private static ListNode merge(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        return R21MergeTwoSortedLists.mergeTwoLists1(merge(lists, start, mid), merge(lists, mid + 1, end));
    }

    /**
     * 优先队列合并：维护当前每个链表没有被合并的元素的最前面一个，每次在这些元素里面选取值最小的元素合并到最终结果中
     *
     * @param lists 数组
     * @return ListNode
     */
    public static ListNode mergeLists3(ListNode[] lists) {
        // 按照值最小的最优先排列
        Queue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));
        for (ListNode node : lists) {
            if (node != null) {
                queue.offer(node);
            }
        }
        ListNode head = new ListNode(0);
        ListNode current = head;
        while (!queue.isEmpty()) {
            current.next = queue.poll();
            current = current.next;
            if (current.next != null) {
                queue.offer(current.next);
            }
        }
        return head.next;
    }

}