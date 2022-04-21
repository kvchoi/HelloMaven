package com.algorithm.leetcode;

import com.algorithm.leetcode.structs.ListNode;
import com.algorithm.leetcode.structs.ListNodeBuilder;

public class No19 {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3};
        ListNode root = ListNodeBuilder.generate(a);
        System.out.println(root);

        ListNode ret = removeNthFromEnd(root, 2);
        System.out.println(ret);
    }

    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        // 不用删
        if (n <= 0) {
            return head;
        }
        // 主指标先走n步
        ListNode current = head;
        for (int i = 0; i < n && current != null; i++) {
            current = current.next;
        }

        // 关键点：从指针进入一个预备节点
        ListNode dummy = new ListNode(0, head);
        ListNode follow = dummy;

        // 主指针继续向前走，走到尽头
        while (current != null) {
            current = current.next;
            follow = follow.next;
        }

        // 执行删除，并清理
        ListNode delete = follow.next;
        follow.next = delete.next;
        delete.next = null;

        // 删除后n个节点的话，可以这样
        //follow.next = null;

        // 取预备节点的下一个为链头
        return dummy.next;

    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        // 不用删
        if (n <= 0) {
            return head;
        }
        // 关键点：从指针进入一个预备节点
        ListNode dummy = new ListNode(0, head);

        // 主指针走n+1步后，从指针进入预备节点
        ListNode follow = null;
        ListNode current = head;
        int i = 0;
        while (current != null) {
            current = current.next;
            if (++i == n) {
                follow = dummy;
            } else if (follow != null) {
                follow = follow.next;
            }
        }

        // 执行删除，并清理
        ListNode delete = follow.next;
        follow.next = delete.next;
        delete.next = null;

        // 删除后n个节点的话，可以这样
        //follow.next = null;

        // 取预备节点的下一个为链头
        return dummy.next;
    }
}
