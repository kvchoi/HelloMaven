package com.algorithm.leetcode;

import com.algorithm.leetcode.structs.ListNode;
import com.algorithm.leetcode.structs.ListNodeBuilder;

public class No61 {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5};
        ListNode root = ListNodeBuilder.generate(a);
        System.out.println(root);

        ListNode ret = rotateRight(root, 6);
        System.out.println(ret);
    }

    //
    public static ListNode rotateRight(ListNode head, int k) {

        if (k == 0 || head == null || head.next == null) {
            return head;
        }

        // 记录长度和最后一个节点
        int n = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            n++;
        }
        // 倍数移动的话，会归原位，不用移
        int tk = k % n;
        if (tk == 0) {
            return head;
        }

        // 把最后一个节点接到头，形成一个环
        tail.next = head;

        // 拨动这个环,尾指针向前走一个大圈
        int add = n - tk;
        while (add-- > 0) {
            tail = tail.next;
        }

        // 解开环
        ListNode ret = tail.next;
        tail.next = null;
        return ret;
    }
}
