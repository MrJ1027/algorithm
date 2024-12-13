package com.jtl.class36;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AdvancedRecursiveTreeVisualizer<E> {
    private boolean isPaused = false; // 控制动画暂停
    private final Stack<TreeNode<E>> callStack = new Stack<>(); // 用于模拟递归栈
    private final List<List<TreeNode<E>>> recursionLevels = new ArrayList<>(); // 存储递归每一层的节点

    public void visualizeTree(TreeNode<E> root) {
        JFrame frame = new JFrame("Advanced Recursive Tree Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        TreePanel treePanel = new TreePanel(root);
        DebugPanel debugPanel = new DebugPanel();

        ControlPanel controlPanel = new ControlPanel(treePanel, debugPanel);

        frame.setLayout(new BorderLayout());
        frame.add(treePanel, BorderLayout.CENTER);
        frame.add(debugPanel, BorderLayout.EAST);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // 启动线程动态递归演示
        new Thread(() -> {
            try {
                buildTreeRecursive(treePanel, root, treePanel.getWidth() / 2, 50, treePanel.getWidth() / 4, 80, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 动态构建树，演示递归过程
     */
    private void buildTreeRecursive(TreePanel treePanel, TreeNode<E> node, int x, int y, int xOffset, int yOffset, int depth) throws InterruptedException {
        if (node == null) return;

        // 模拟方法压栈
        callStack.push(node);
        if (recursionLevels.size() <= depth) {
            recursionLevels.add(new ArrayList<>());
        }
        recursionLevels.get(depth).add(node);

        treePanel.setCurrentNode(node, depth);
        treePanel.repaint();

        Thread.sleep(500);
        waitForResume();

        // 绘制左子树
        if (node.left != null) {
            buildTreeRecursive(treePanel, node.left, x - xOffset, y + yOffset, xOffset / 2, yOffset, depth + 1);
        }

        // 绘制右子树
        if (node.right != null) {
            buildTreeRecursive(treePanel, node.right, x + xOffset, y + yOffset, xOffset / 2, yOffset, depth + 1);
        }

        // 模拟方法出栈
        callStack.pop();
        treePanel.repaint();
        Thread.sleep(500);
        waitForResume();
    }

    private void waitForResume() throws InterruptedException {
        while (isPaused) {
            Thread.sleep(200);
        }
    }

    /**
     * 树的绘制面板
     */
    private class TreePanel extends JPanel {
        private final TreeNode<E> root;
        private TreeNode<E> currentNode;
        private int currentDepth;

        public TreePanel(TreeNode<E> root) {
            this.root = root;
        }

        public void setCurrentNode(TreeNode<E> node, int depth) {
            this.currentNode = node;
            this.currentDepth = depth;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (root != null) {
                drawTree(g, root, getWidth() / 2, 50, getWidth() / 4, 80, 0);
            }

            // 高亮当前节点
            if (currentNode != null) {
                g.setColor(Color.RED);
                g.fillOval(currentNode.x - 25, currentNode.y - 25, 50, 50);
                g.setColor(Color.WHITE);
                g.drawString(String.valueOf(currentNode.val), currentNode.x - 10, currentNode.y + 5);
            }
        }

        private void drawTree(Graphics g, TreeNode<E> node, int x, int y, int xOffset, int yOffset, int depth) {
            if (node == null) return;

            node.x = x;
            node.y = y;

            // 绘制左子树
            if (node.left != null) {
                int childX = x - xOffset;
                int childY = y + yOffset;
                g.setColor(Color.BLACK);
                g.drawLine(x, y, childX, childY);
                drawTree(g, node.left, childX, childY, xOffset / 2, yOffset, depth + 1);
            }

            // 绘制右子树
            if (node.right != null) {
                int childX = x + xOffset;
                int childY = y + yOffset;
                g.setColor(Color.BLACK);
                g.drawLine(x, y, childX, childY);
                drawTree(g, node.right, childX, childY, xOffset / 2, yOffset, depth + 1);
            }

            // 绘制节点
            g.setColor(Color.BLUE);
            g.fillOval(x - 20, y - 20, 40, 40);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(node.val), x - 10, y + 5);
        }
    }

    /**
     * 调试面板
     */
    private class DebugPanel extends JPanel {
        private JLabel nodeInfoLabel;
        private JLabel recursionInfoLabel;

        public DebugPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            nodeInfoLabel = new JLabel("Current Node: ");
            recursionInfoLabel = new JLabel("Recursion Level: ");
            add(nodeInfoLabel);
            add(recursionInfoLabel);
        }

        public void updateDebugInfo(TreeNode<E> node, int depth) {
            nodeInfoLabel.setText("Current Node: " + (node == null ? "None" : node.val));
            recursionInfoLabel.setText("Recursion Level: " + depth);
        }
    }

    /**
     * 控制面板
     */
    private class ControlPanel extends JPanel {
        public ControlPanel(TreePanel treePanel, DebugPanel debugPanel) {
            JButton pauseButton = new JButton("Pause");
            pauseButton.addActionListener(e -> isPaused = true);

            JButton resumeButton = new JButton("Resume");
            resumeButton.addActionListener(e -> isPaused = false);

            JButton stepBackButton = new JButton("Step Back");
            stepBackButton.addActionListener(e -> {
                if (!callStack.isEmpty()) {
                    TreeNode<E> lastNode = callStack.pop();
                    treePanel.repaint();
                    debugPanel.updateDebugInfo(lastNode, callStack.size());
                }
            });

            add(pauseButton);
            add(resumeButton);
            add(stepBackButton);
        }
    }
}
