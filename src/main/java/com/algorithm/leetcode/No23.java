package com.algorithm.leetcode;

import com.algorithm.leetcode.structs.ListNode;
import com.algorithm.leetcode.structs.ListNodeBuilder;

import java.util.Arrays;

public class No23 {
    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3};
        ListNode first = ListNodeBuilder.generate(a);
        System.out.println(first);

        int[] b = new int[]{1, 4, 7};
        ListNode second = ListNodeBuilder.generate(b);
        System.out.println(second);

        int[] c = new int[]{4, 5, 6};
        ListNode third = ListNodeBuilder.generate(c);
        System.out.println(third);

        ListNode[] lists = new ListNode[]{first, second, third};

        ListNode ret = mergeKLists(lists);
        System.out.println(ret);
    }

    // =======================分治递归（数组索引）============================

    public static ListNode mergeKLists(ListNode[] lists) {
        return mergeLists(lists, 0, lists.length - 1);
    }

    public static ListNode mergeLists(ListNode[] lists, int l, int r) {
        if (l == r) {
            return lists[l];
//            return lists[r];
        }
        if (l > r) {
            return null;
        }
        int div = (l + r) / 2;
        return No21.mergeTwoLists(mergeLists(lists, l, div),
                mergeLists(lists, div + 1, r));
    }

    //==========================分治递归（数据切割）===============================

    public static ListNode mergeKLists0(ListNode[] lists) {
        int size = lists.length;
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return lists[0];
        }
        int div = size / 2;
        return No21.mergeTwoLists(mergeKLists(Arrays.copyOfRange(lists, 0, div)),
                mergeKLists(Arrays.copyOfRange(lists, div, lists.length)));
    }

    //==========================循环分步合并===============================

    public static ListNode mergeKLists1(ListNode[] lists) {
        ListNode ans = null;
        for (int i = 0; i < lists.length; i++) {
            ans = No21.mergeTwoLists(ans, lists[i]);
        }
        return ans;
    }

    //===========================单元素合并==============================

    public static ListNode mergeKLists2(ListNode[] lists) {
        //准备一条新表
        ListNode ans = new ListNode(0, null);
        ListNode cur = ans;
        //首次排序
        sort(lists);

        while (lists.length > 0 && lists[0] != null) {
            ListNode tmp = lists[0];
            lists[0] = tmp.next;
            cur.next = tmp;
            cur = cur.next;
            //单个节点失序，需要重排序
            sort(lists);
        }
        return ans.next;
    }

    /**
     * 通过重新排序，解决取一个小值的问题。
     * 可以用优先队列来解决取一个最小值的问题。
     */
    private static void sort(ListNode[] lists) {
        // 针对大部分元素有序的情况，使用TimSort算法已经是最优解
        Arrays.sort(lists, (o1, o2) -> {
            if (o1 == o2) {
                // 相同或全null，获得同优先级
                return 0;
            } else if (o1 == null) {
                // o1=null值放到最后，o2获得优先级
                return 1;
            } else if (o2 == null) {
                // o2=null值放到最后，o1获得优先级
                return -1;
            } else {
                return o1.val - o2.val;
            }
        });
    }

}
