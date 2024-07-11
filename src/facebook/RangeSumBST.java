package facebook;

import topInterviewQuestions.utils.LeetUtils;

public class RangeSumBST {
    public static void main(String[] args) {

        int sum = 0;

        topInterviewQuestions.utils.TreeNode root = new topInterviewQuestions.utils.TreeNode(10, new topInterviewQuestions.utils.TreeNode(5, new topInterviewQuestions.utils.TreeNode(3), new topInterviewQuestions.utils.TreeNode(7)), new topInterviewQuestions.utils.TreeNode(7, null, new topInterviewQuestions.utils.TreeNode(18)));




    }

    private static int calculateSum(topInterviewQuestions.utils.TreeNode root, int low, int high) {
        int sum = 0;

        if (root.val >= low && root.val <= high) {
            sum += root.val;
        }

        if (root.left != null && low < root.val) {//7<=10
            sum += calculateSum(root.left, low, high);

        }
        if (root.right != null && high > root.val) {//10>=15
            sum += calculateSum(root.right, low, high);

        }
return sum;

    }
}
