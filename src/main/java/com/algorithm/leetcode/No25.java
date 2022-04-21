package com.algorithm.leetcode;

import com.algorithm.leetcode.structs.ListNode;
import com.algorithm.leetcode.structs.ListNodeBuilder;

import java.util.List;

/**
 * K个一组反转
 */
public class No25 {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5, 6};
        ListNode first = ListNodeBuilder.generate(a);
        System.out.println(first);

        ListNode ret = reverseKGroup(first, 2);
        System.out.println(ret);
    }

    //模拟法
    public static ListNode reverseKGroup(ListNode head, int k) {
        // 反转头，需要一个临时节点
        ListNode dummy = new ListNode(0, head);
        // 操作位置
        ListNode pre = dummy;

        while (head != null) {
            // 先定位好反转区间，如果不足k个，则终止
            ListNode tail = pre;
            for (int i = 0; i < k; i++) {
                tail = tail.next;
                if (tail == null) {
                    return dummy.next;
                }
            }
            // 找到k区间，定位下一区间起始，方便后面操作
            ListNode next = tail.next;

            // 执行k区间反转
            ListNode[] headTail = No92.reverseListBetween(head, tail);
            head = headTail[0];
            tail = headTail[1];
            // 把子链表重新接回原链表
            pre.next = head;
            tail.next = next;

            // 前进
            pre = tail;
            head = tail.next;
        }

        return dummy.next;

    }



}
