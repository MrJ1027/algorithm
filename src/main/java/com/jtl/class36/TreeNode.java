package com.jtl.class36;

public class TreeNode<E> {
        E val;
        TreeNode left;
        TreeNode right;

        int x;
        int y;

        TreeNode() {
        }

        TreeNode(E val) {
            this.val = val;
        }

        TreeNode(E val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }