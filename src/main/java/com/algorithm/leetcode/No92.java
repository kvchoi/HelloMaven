package com.algorithm.leetcode;

import com.algorithm.leetcode.structs.ListNode;
import com.algorithm.leetcode.structs.ListNodeBuilder;

public class No92 {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5, 6};
        ListNode first = ListNodeBuilder.generate(a);
        System.out.println(first);

        ListNode ret = reverseBetween(first, 2, 4);
        System.out.println(ret);
    }

    // 模拟法
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        // 如果left=1，可能就要反转head了，预备一个虚节点先
        ListNode dummy = new ListNode(0, head);
        // 定位到start的前一个位置
        ListNode pre = dummy;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode start = pre.next;
        // 定位到end的前一个位置
        ListNode end = dummy;
        for (int i = 0; i < right; i++) {
            end = end.next;
        }
        // 执行区间反转操作
        ListNode[] headTail = reverseListBetween(start, end);
        // 把新的head接上
        pre.next = headTail[0];

        return dummy.next;
    }

    /**
     * 双指针区间反转(改进)
     */
    public static ListNode[] reverseListBetween(ListNode head, ListNode tail) {
        // 新的头尾指针
        ListNode[] headTail = new ListNode[2];
        headTail[0] = null;
        headTail[1] = null;
        if (head == null) {
            return headTail;
        }

        // 暂时断开尾巴
        ListNode next = null;
        if (tail != null) {
            next = tail.next;
            tail.next = null;
        }

        // 双指针反转
        ListNode l = null;
        ListNode r = head;
        while (r != null) {
            // 暂存
            ListNode t = r.next;
            // 反转
            r.next = l;
            // 前进
            l = r;
            r = t;
        }
        // 接上断尾
        head.next = next;

        headTail[0] = l;
        headTail[1] = head;
        return headTail;
    }

}
