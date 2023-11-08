package topInterviewQuestions.easy.trees;

import topInterviewQuestions.utils.TreeNode;

public class ConvertSortedArrayToBinarySearch {
    public static void main(String[] args) {
        int[] nums = new int[]{-10, -3, 0, 5, 9};
        TreeNode node = sortedArrayToBST(nums);
        System.out.println(node);
    }

    private static TreeNode sortedArrayToBST(int[] nums) {

        if (nums.length == 0) {
            return null;
        }
        return convertSortedArrayToBinarySearch(nums, 0, nums.length - 1);

    }

    private static TreeNode convertSortedArrayToBinarySearch(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int middle = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[middle]);
        root.left = convertSortedArrayToBinarySearch(nums, left, middle - 1);
        root.right = convertSortedArrayToBinarySearch(nums, middle + 1, right);
        return root;
    }
}
