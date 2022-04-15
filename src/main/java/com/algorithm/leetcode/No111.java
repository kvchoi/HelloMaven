package com.algorithm.leetcode;

import com.algorithm.leetcode.structs.TreeNode;
import com.algorithm.leetcode.structs.TreeNodeBuilder;

public class No111 {

    public static void main(String[] args) {
        Integer[] a = new Integer[]{3, 9, 20, null, null, 15, 7};
        TreeNode root = TreeNodeBuilder.generateTree(a);
        System.out.println(minDepth(root));
    }

    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //叶子节点，自动获得1层，并退出
        if (root.left == null && root.right == null) {
            return 1;
        }

        // 左孩子和右孩子，看看哪个最小
        int min = Integer.MAX_VALUE;
        if (root.left != null) {
            min = Math.min(minDepth(root.left), min);
        }
        if (root.right != null) {
            min = Math.min(minDepth(root.right), min);
        }

        // 这个是关键，当前递归层退出时 +1，计算树高
        return min + 1;
    }

}
