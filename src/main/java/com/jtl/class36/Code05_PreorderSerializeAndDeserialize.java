package com.jtl.class36;

import java.util.Arrays;

/**
 * @author Mr.J
 * @time 2024/11/22 13:04
 * @Version 1.0
 * @description 先序遍历序列化和反序列化
 */
public class Code05_PreorderSerializeAndDeserialize {

    public static void main(String[] args) {
        TreeNode<Character> node4 = new TreeNode<>('d', null, null);
        TreeNode<Character> node3 = new TreeNode<>('c', node4, null);
        TreeNode<Character> node2 = new TreeNode<>('b', null, null);
        TreeNode<Character> node1 = new TreeNode<>('a',node2,node3);
        Codec codec = new Codec();
        String serialize = codec.serialize(node1);
        System.out.println("序列化后的结果:" + serialize);
    }
    public static class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serialize(root,sb);
            return sb.toString();
        }

        public void serialize(TreeNode root,StringBuilder sb){
            if(root == null){
                sb.append("#,");
                return;
            }
            sb.append(root.val + ",");
            serialize(root.left,sb);
            serialize(root.right,sb);
        }

        //记录当前消费到哪个位置,做成全局的,任何递归过程都能直接拿到
        private static int cnts;

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] vals = data.split(",");
            cnts = 0;
            return deserialize(vals);
        }

        public TreeNode deserialize(String[] vals){
            String val = vals[cnts++];
            if(val.equals("#")){
                return null;
            }else{
                TreeNode<Character> head = new TreeNode<>(val.charAt(0));
                head.left = deserialize(vals);
                head.right = deserialize(vals);
                return head;
            }

        }
    }

}
