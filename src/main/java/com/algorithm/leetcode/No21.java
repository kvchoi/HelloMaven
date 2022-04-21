package com.algorithm.leetcode;

import com.algorithm.leetcode.structs.ListNode;
import com.algorithm.leetcode.structs.ListNodeBuilder;

public class No21 {
    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3};
        ListNode first = ListNodeBuilder.generate(a);
        System.out.println(first);

        int[] b = new int[]{1, 4, 7};
        ListNode second = ListNodeBuilder.generate(b);
        System.out.println(second);

        ListNode ret = mergeTwoLists(first, second);
        System.out.println(ret);
    }

    public static ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        //准备一条新表
        ListNode ans = new ListNode(0, null);
        ListNode cur = ans;
        // 遍历两个表，并从其中取一个合适的元素，直接到两表全部元素被取走为止
        while (list1 != null || list2 != null) {
            if (list1 != null && list2 != null) {
                // 两个都没取完，要比较
                if (list1.val <= list2.val) {
                    cur.next = list1;
                    list1 = list1.next;
                } else {
                    cur.next = list2;
                    list2 = list2.next;
                }
                cur = cur.next;
            } else if (list1 != null) {
                // list1还有，list2取完
                cur.next = list1;
                list1 = null;
            } else {
                // list1取完，list2还有
                cur.next = list2;
                list2 = null;
            }
        }
        return ans.next;
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        // 以list1为底槽，list2取元素插入其中
        ListNode ans = new ListNode(Integer.MIN_VALUE, list1);
        ListNode pre = ans;
        ListNode cur = ans.next;
        // 遍历list2
        while (list2 != null) {
            if (cur == null) {
                // list1的插入位置用完，直接把list2接上就行
                pre.next = list2;
                break;
            } else if (cur.val <= list2.val) {
                // list1的插入位置不合适，则向前遍历一次
                cur = cur.next;
                pre = pre.next;
            } else {
                // 找到合适的插入位置，开始插入
                pre.next = list2;
                list2 = list2.next;
                pre.next.next = cur;
                pre = pre.next;
            }
        }
        return ans.next;
    }
}
