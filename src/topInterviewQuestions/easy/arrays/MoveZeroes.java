package topInterviewQuestions.easy.arrays;

import static topInterviewQuestions.utils.LeetUtils.printIntArray;

public class MoveZeroes {
    public static void main(String[] args) {
        printIntArray(moveZeroes(new int[]{1, 0, 3, 0, 0, 12}));
    }

    public static int[] moveZeroes(int[] nums) {
        int zeroIndex = 0;
        for (int index = 0; index < nums.length; index++) {
            //swap when num != 0. If all the numbers are non-zero,
            // then it will swap for every number (index=zeroIndex)
            // but this swap will not impact anything
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
