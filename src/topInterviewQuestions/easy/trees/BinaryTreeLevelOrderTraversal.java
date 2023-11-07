package topInterviewQuestions.easy.trees;

import topInterviewQuestions.utils.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeLevelOrderTraversal {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3, new TreeNode(9, new TreeNode(6), new TreeNode(11)),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        List<List<Integer>> lists = levelOrder(root);
        System.out.println(lists);

    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            List<Integer> levels = new ArrayList<>();
            int size = q.size();

            for (int i = 0; i < size; i++) {
                TreeNode current = q.poll();
                levels.add(current.val);
                if (current.left != null) {
                    q.add(current.left);
                }
                if (current.right != null) {
                    q.add(current.right);
                }

            }

            if (levels.size() > 0) {
                res.add(levels);
            }
        }
        return res;

    }

}
