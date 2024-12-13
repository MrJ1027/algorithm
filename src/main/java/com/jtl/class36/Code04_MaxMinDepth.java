package com.jtl.class36;


import sun.reflect.generics.tree.Tree;

/**
 * @author Mr.J
 * @time 2024/11/22 12:14
 * @Version 1.0
 * @description
 */
public class Code04_MaxMinDepth {

    public static void main(String[] args) {
        TreeNode node5 = new TreeNode(7, null, null);
        TreeNode node4 = new TreeNode(15, null, null);
        TreeNode node3 = new TreeNode(20, node4, node5);
        TreeNode node2 = new TreeNode(9, null, null);
        TreeNode node1 = new TreeNode(3, node2, node3);
        int maxDepth = maxDepth(node1);
        int minDepth = minDepth(node1);
        System.out.println("最大深度:" + maxDepth);
        System.out.println("最小深度:" + minDepth);
    }


    public static int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left,right) + 1;
    }

    public static int minDepth(TreeNode root){
        if (root == null) {
            // 当前的树是空树
            return 0;
        }
        if (root.left == null && root.right == null) {
            // 当前root是叶节点
            return 1;
        }
        int ldeep = Integer.MAX_VALUE;
        int rdeep = Integer.MAX_VALUE;
        if (root.left != null) {
            ldeep = minDepth(root.left);
        }
        if (root.right != null) {
            rdeep = minDepth(root.right);
        }
        return Math.min(ldeep, rdeep) + 1;

    }

}
