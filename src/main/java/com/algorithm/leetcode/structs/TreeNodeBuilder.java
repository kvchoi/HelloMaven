package com.algorithm.leetcode.structs;

import java.util.LinkedList;
import java.util.Queue;

public class TreeNodeBuilder {
    public static TreeNode generateTree(Integer[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        Queue<TreeNode> q = new LinkedList<>();

        TreeNode root = new TreeNode(nums[0]);
        q.offer(root);

        int k = 1;
        while (k < nums.length) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode p = q.poll();
                if (nums[k] != null) {
                    TreeNode node = new TreeNode(nums[k]);
                    p.left = node;
                    q.offer(node);
                } else {
                    p.left = null;
                }
                k++;
                if (nums[k] != null) {
                    TreeNode node = new TreeNode(nums[k]);
                    p.right = node;
                    q.offer(node);
                } else {
                    p.right = null;
                }
                k++;
            }
        }

        return root;
    }
}
