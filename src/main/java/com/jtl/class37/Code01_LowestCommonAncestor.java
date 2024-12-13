package com.jtl.class37;


/**
 * @author Mr.J
 * @time 2024/11/26 21:43
 * @Version 1.0
 * @description
 */
public class Code01_LowestCommonAncestor {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //如果当前节点是空,返回null;如果找到p和q其中一个,直接返回
        if(root == null || root == p || root == q){
            return root;
        }
        TreeNode l = lowestCommonAncestor(root.left,p,q); //左树搜索
        TreeNode r = lowestCommonAncestor(root.right,p,q);//右树搜索
        //如果左边返回值不为空,右边返回值也不为空
        //说明p和q一定在左右两树上,当前节点就是它们最近的公共祖先
        //把祖先往上返回
        if(l != null && r != null){
            return root;
        }
        //如果左边返回值和右边返回值都为空
        //说明当前树下方不存在p和q,把空往上返回
        if(l == null && r == null){
            return null;
        }
        //否则,左右返回值有一个不为空,直接把这个不为空的往上传递
        return l != null ? l : r;
    }
}




