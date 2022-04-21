package com.algorithm.leetcode;

import com.algorithm.leetcode.structs.ListNode;
import com.algorithm.leetcode.structs.ListNodeBuilder;

/**
 * 链表反转相关的有：
 * 链表两两反转、K个一组反转、全链表反转
 */
public class No24 {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4};
        ListNode first = ListNodeBuilder.generate(a);
        System.out.println(first);

        ListNode ret = swapPairs(first);
        System.out.println(ret);
    }

    // 前后交换位置
    public static ListNode swapPairs2(ListNode head) {
        // 头节点需要切换，需要一个预备节点
        ListNode dummy = new ListNode(0, head);

        // 当前操作起点
        ListNode current = dummy;
        // 临时节点保存切断元素
        ListNode temp = current.next;
        // 如果没有切断元素，或者这个元素的下一跳交换元素为空，则不需要再操作了
        while (temp != null && temp.next != null) {
            // 交换
            current.next = temp.next;
            temp.next = temp.next.next;
            current.next.next = temp;
            // 前进
            current = temp;
            temp = current.next;
        }
        return dummy.next;
    }

    // 循环拼接生成新链表
    public static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0, null);
        ListNode current = dummy;
        ListNode temp = null;

        // 把旧链表元素全部迁走
        while (head != null) {
            // 1，旧链表保存一个元素，其余迁走
            current.next = head.next;
            // 2，旧链表余下的那个元素暂存起来
            head.next = null;
            temp = head;
            // 3，新链表操作位前移（如果旧链表只有一个元素时，第一次接上来的是一个空链表）
            if (current.next != null) {
                current = current.next;
            }
            // 4，新链表保存操作位的元素，其余归还给旧链表
            head = current.next;
            // 5，暂存元素加入新链表
            current.next = temp;
            // 6，新链表操作位前移
            current = current.next;
        }
        return dummy.next;
    }

}
