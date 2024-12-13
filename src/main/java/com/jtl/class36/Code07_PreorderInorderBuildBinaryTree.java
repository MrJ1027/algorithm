package com.jtl.class36;

import sun.reflect.generics.tree.Tree;

import java.util.HashMap;

/**
 * @author Mr.J
 * @time 2024/11/23 11:18
 * @Version 1.0
 * @description
 */
public class Code07_PreorderInorderBuildBinaryTree {

    public static void main(String[] args) {
        int[] preorder = {1,2,4,5,3,6,7};
        int[] inorder = {4,2,5,1,7,6,3};
        TreeNode treeNode = buildTree(preorder, inorder);
    }

    private static HashMap<Integer,Integer> map = new HashMap<>();

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0
         || preorder.length != inorder.length){
            return null;
        }
        //哈希表记录中序遍历数组每个值的位置,方便去找头结点的值
        map.clear();
        for(int i = 0; i < inorder.length; i++){
            map.put(inorder[i],i);
        }
        return createHead(preorder,0,preorder.length - 1,inorder,0,inorder.length - 1);
    }

    /**
     * 从前序范围l1-r1;中序范围l2-r2;给我搞出整棵树
     * @param preorder 前序遍历数组
     * @param l1 前序遍历起始位置
     * @param r1 前序遍历终止位置
     * @param inorder 中序遍历
     * @param l2 中序起始
     * @param r2 中序终止
     * @return
     */
    public static TreeNode createHead(int[] preorder,int l1,int r1,int[] inorder,int l2,int r2){
        //如果l1比r1还大,返回null
        if(l1 > r1){
            return null;
        }
        //建立出头结点
        TreeNode head = new TreeNode(preorder[l1]);
        //如果只有一个节点,直接返回头结点
        if(l1 == r1){
            return head;
        }
        //根据头结点的值去中序数组中找该值在哪个位置
        //然后确定出左树和右数,递归处理
        int k = map.get(head.val);
        //左树范围确定左树
        head.left = createHead(preorder,l1 + 1,l1 + k - l2,inorder,l2,k - 1);
        //右树范围确定右数
        head.right = createHead(preorder,l1 + k - l2 + 1,r1,inorder,k + 1,r2);
        return head;
    }
}
