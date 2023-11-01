package topInterviewQuestions.easy.trees;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class MaximumDepth {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        TreeNode node = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(maxDepth(node));
        System.out.println(maxDepthBFS(node));
    }

    //recursion
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    //BFS
    public static int maxDepthBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Deque<TreeNode> dq = new ArrayDeque<>();
        int depth = 0;
        int next = 0;
        TreeNode current;
        dq.offer(root);//Adds root TreeNode and size will be 1
        while (!dq.isEmpty()) {
            depth++; // it will increase to 2 when iterated 2nd time
            next = dq.size(); // first time it will be 1 and second time 2
            for (int i = 0; i < next; ++i) {
                current = dq.poll(); // retrieves and removed the TreeNode
                if (current.left != null) {
                    dq.offer(current.left);
                }
                if (current.right != null) {
                    dq.offer(current.right);
                }
            }

        }
        return depth;
    }

    //iteration
    public static int maxDepthIteration(TreeNode root) {

        return 0;

    }
}
