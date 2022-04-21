package com.algorithm.leetcode;

import com.algorithm.leetcode.structs.ListNode;
import com.algorithm.leetcode.structs.ListNodeBuilder;

/**
 * 反转链表
 */
public class No206 {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4};
        ListNode first = ListNodeBuilder.generate(a);
        System.out.println(first);

        ListNode ret = reverseList(first);
        System.out.println(ret);
    }

    // 循环拼接生成新链表
    public static ListNode reverseList2(ListNode head) {
        ListNode dummy = new ListNode(0, null);
        ListNode tmp = null;

        // 旧链表元素全部迁走
        while (head != null) {
            // 暂存第一元素后，将其它从旧链表移除
            tmp = head;
            head = head.next;
            // 暂存元素切断旧链表，并转为指向新链表
            tmp.next = null;
            if (dummy.next != null) {
                tmp.next = dummy.next;
            }
            // 暂存元素加入新链表
            dummy.next = tmp;
        }

        return dummy.next;
    }

    // 双指针直接前后交换
    public static ListNode reverseList(ListNode head) {
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
        return l;
    }

}
