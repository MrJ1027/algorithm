package com.jtl.class36;

/**
 * @author Mr.J
 * @time 2024/11/24 19:24
 * @Version 1.0
 * @description
 */
public class DFS {

    private static int flag = 0;

    public static void main(String[] args) {
        TreeNode node4 = new TreeNode(7, null, null);
        TreeNode node3 = new TreeNode(15, null, null);
        TreeNode node2 = new TreeNode(20, node3, node4);
        TreeNode node1 = new TreeNode(9, null, null);
        TreeNode head = new TreeNode(3,node1,node2);
        postorder(head);
        printMsg();
        inorder(head);
        printMsg();


    }

    private static void printMsg(){
        if(flag > 0){
            System.out.println("preorder:");
        }else if(flag < 0) {
            System.out.println("postorder:");
        }else{
            System.out.println("inorder:");
        }
        flag = 0;
    }

    public static void postorder(TreeNode head){
        if(head == null){
            flag--;
            return;
        }
        postorder(head.left);
        postorder(head.right);
        System.out.print(head.val + " ");
    }

    public static void inorder(TreeNode head){
        if(head == null){
            return;
        }
        inorder(head.left);
        System.out.print(head.val + " ");
        inorder(head.right);
    }

    public static void preorder(TreeNode head){
        if(head == null){
            flag++;
            return;
        }
        System.out.print(head.val + " ");
        preorder(head.left);
        preorder(head.right);
    }
}
