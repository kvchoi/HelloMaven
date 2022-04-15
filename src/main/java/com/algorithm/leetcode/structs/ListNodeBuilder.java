package com.algorithm.leetcode.structs;

public class ListNodeBuilder {

    public static ListNode generate(int[] nums) {
        if (nums == null) {
            return null;
        }
        ListNode head = new ListNode();
        ListNode cur = head;

        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                head.val = nums[0];
            } else {
                cur.next = new ListNode(nums[i]);
                cur = cur.next;
            }
        }
        return head;
    }
}
