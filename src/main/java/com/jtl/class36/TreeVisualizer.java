package com.jtl.class36;

import javax.swing.*;
import java.awt.*;

public class TreeVisualizer<E> {

    /**
     * 启动一个GUI界面，绘制树的结构
     *
     * @param root 树的根节点
     */
    public void visualizeTree(TreeNode<E> root) {
        JFrame frame = new JFrame("Tree Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        TreePanel treePanel = new TreePanel(root);
        frame.add(treePanel);

        frame.setVisible(true);
    }

    // 树的绘制面板
    private class TreePanel extends JPanel {
        private final TreeNode<E> root;

        public TreePanel(TreeNode<E> root) {
            this.root = root;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (root != null) {
                drawTree(g, root, getWidth() / 2, 50, getWidth() / 4, 50);
            }
        }

        /**
         * 递归绘制树
         *
         * @param g       画布
         * @param node    当前节点
         * @param x       节点的x坐标
         * @param y       节点的y坐标
         * @param xOffset 水平方向偏移量
         * @param yOffset 垂直方向偏移量
         */
        private void drawTree(Graphics g, TreeNode<E> node, int x, int y, int xOffset, int yOffset) {
            if (node == null) {
                return;
            }

            // 绘制节点值
            g.setColor(Color.BLUE);
            g.fillOval(x - 20, y - 20, 40, 40); // 圆形表示节点
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(node.val), x - 10, y + 5);

            // 绘制左子树
            if (node.left != null) {
                int childX = x - xOffset;
                int childY = y + yOffset;
                g.setColor(Color.BLACK);
                g.drawLine(x, y, childX, childY); // 连线
                drawTree(g, node.left, childX, childY, xOffset / 2, yOffset);
            }

            // 绘制右子树
            if (node.right != null) {
                int childX = x + xOffset;
                int childY = y + yOffset;
                g.setColor(Color.BLACK);
                g.drawLine(x, y, childX, childY); // 连线
                drawTree(g, node.right, childX, childY, xOffset / 2, yOffset);
            }
        }
    }
}
