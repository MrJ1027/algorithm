package com.jtl.class36;

/**
 * @author Mr.J
 * @time 2024/11/23 10:30
 * @Version 1.0
 * @description 层序遍历序列化二叉树
 */
public class Code06_LevelorderSerializeAndDeserialize {

    public static void main(String[] args) {
        TreeNode<Character> node4 = new TreeNode<>('d', null, null);
        TreeNode<Character> node3 = new TreeNode<>('c', node4, null);
        TreeNode<Character> node2 = new TreeNode<>('b', null, null);
        TreeNode<Character> node1 = new TreeNode<>('a',node2,node3);
        String serialize = serialize(node1);
        System.out.println("序列化后:" + serialize);
    }

    private static TreeNode[] queue = new TreeNode[10001];
    private static int l,r;

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if(root == null){
            return sb.toString();
        }
        l = r = 0;
        queue[r++] = root;
        sb.append(root.val + ",");
        while(l < r){
            int size = r - l;
            for(int i = 0; i < size; i++){
                TreeNode polled = queue[l++];
                //有左加左的值
                if(polled.left != null){
                    sb.append(polled.left.val + ",");
                    queue[r++] = polled.left;
                }else{
                    //没左加个占位符
                    sb.append("#,");
                }
                if(polled.right != null){
                    sb.append(polled.right.val + ",");
                    queue[r++] = polled.right;
                }else{
                    sb.append("#,");
                }
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        if(data.equals("")){
            return null;
        }
        String[] vals = data.split(",");
        int cnt = 0;
        TreeNode head = generate(vals[cnt++]);//建立头结点
        l = r = 0;
        queue[r++] = head;
        while(l < r){
            TreeNode polled = queue[l++];
            polled.left = generate(vals[cnt++]);
            polled.right = generate(vals[cnt++]);
            if(polled.left != null){
                queue[r++] = polled.left;
            }
            if(polled.right != null){
                queue[r++] = polled.right;
            }
        }
        return head;
    }

    /**
     * 根据字符串创建节点
     * @param cur
     * @return
     */
    private static TreeNode generate(String cur){
        return cur.equals("#") ? null : new TreeNode(Integer.valueOf(cur));
    }
}
