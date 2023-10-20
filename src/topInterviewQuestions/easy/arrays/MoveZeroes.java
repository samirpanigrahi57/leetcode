package topInterviewQuestions.easy.arrays;

import static topInterviewQuestions.LeetUtils.printArray;

public class MoveZeroes {
    public static void main(String[] args) {
        printArray(moveZeroes(new int[]{1, 0, 3, 0, 0, 12}));
    }

    public static int[] moveZeroes(int[] nums) {
        int zeroIndex = 0;
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != 0) {
                swap(nums, index, zeroIndex);
                zeroIndex++;
            }
        }
        return nums;
    }

    private static void swap(int[] nums, int index, int zeroIndex) {
        int temp = nums[zeroIndex];
        nums[zeroIndex] = nums[index];
        nums[index] = temp;
    }

}
