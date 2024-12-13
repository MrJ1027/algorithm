package com.jtl.class36;

import java.util.HashMap;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Code07_InorderPostorderBuildBinaryTree {

    public static void main(String[] args) {
//        TreeNode node4 = new TreeNode(7, null, null);
//        TreeNode node3 = new TreeNode(15, null, null);
//        TreeNode node2 = new TreeNode(20, node3, node4);
//        TreeNode node1 = new TreeNode(9, null, null);
//        TreeNode head = new TreeNode(3,node1,node2);
        int[] inorder = {9,3,15,20,7};
        int[] postorder = {9,15,7,20,3};
        buildTree(inorder,postorder);
    }

    private static HashMap<Integer,Integer> map = new HashMap<>();

    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        if(inorder == null || postorder == null || inorder.length != postorder.length){
            return null;
        }
        map.clear();
        for(int i = 0; i < inorder.length; i++){
            map.put(inorder[i],i);
        }
        return createHead(inorder,0,inorder.length - 1,postorder,0,postorder.length - 1);
    }

    public static TreeNode createHead(int[] inorder,int l1,int r1,int[] postorder,int l2,int r2){
        if(l2 > r2){
            return null;
        }
        TreeNode head = new TreeNode(postorder[r2]);
        if(l2 == r2){
            return head;
        }
        int k = map.get(head.val);
        head.left = createHead(inorder,l1,k,postorder,l2,l2 + k - l1 - 1);
        head.right = createHead(inorder,k + 1,r1,postorder,l2 + k - l1,r2 - 1);
        return head;
    }
}