package facebook;


import java.util.*;

public class BinaryTreeVerticalOrder {
    public static void main(String[] args) {

        TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        List<List<Integer>> lists = verticalOrder(root);
        System.out.println(lists);

    }

    public static List<List<Integer>> verticalOrder(TreeNode root) {
        Map<Integer, List<Integer>> order = new HashMap<>();
        Map<TreeNode, Integer> weight = new HashMap<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        weight.put(root, 0);


        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int column = weight.get(node);

            order.computeIfAbsent(column, list -> new ArrayList<>()).add(node.val);

            if (node.left != null) {
                queue.add(node.left);
                weight.put(node.left, column - 1);
            }

            if (node.right != null) {
                queue.add(node.right);
                weight.put(node.right, column + 1);
            }
        }

        return new ArrayList<>(order.values());

    }


}

class TreeNode {
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

