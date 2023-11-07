package topInterviewQuestions.easy.trees;

import topInterviewQuestions.utils.TreeNode;

public class ValidBST {
    public static void main(String[] args) {

        TreeNode root = new TreeNode(20, new TreeNode(10, new TreeNode(5), new TreeNode(15)),
                new TreeNode(40, new TreeNode(30), new TreeNode(50)));
        System.out.println(isValidBST(root));
        TreeNode inValid = new TreeNode(5, new TreeNode(1), new TreeNode(4, new TreeNode(3), new TreeNode(6)));
        System.out.println(isValidBST(inValid));
        TreeNode invalid1 = new TreeNode(-2147483648, new TreeNode(-2147483648), null);
        System.out.println(isValidBST(invalid1));


    }

    private static boolean isValidBST(TreeNode root) {


        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValidBST(TreeNode root, long minValue, long maxValue) {
        if (root == null) {
            return true;
        }
        if (root.val <= minValue || root.val >= maxValue) {
            return false;
        }
        return isValidBST(root.left, minValue, root.val ) && isValidBST(root.right, root.val, maxValue);

    }
}
