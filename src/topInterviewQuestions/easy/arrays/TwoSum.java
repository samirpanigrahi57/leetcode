package topInterviewQuestions.easy.arrays;

import topInterviewQuestions.LeetUtils;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args) {
        LeetUtils.printArray(twoSumBruteForce(new int[]{2, 7, 11, 15}, 18));
        LeetUtils.printArray(twoSumOnePass(new int[]{2, 7, 11, 15}, 18));
    }

    public static int[] twoSumOnePass(int[] nums, int target) {
        Map<Integer, Integer> twoSum = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (twoSum.containsKey(diff)) {
                return new int[]{nums[i], diff};
            }
            twoSum.put(nums[i], i);
        }
        return null;
    }

    public static int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((target - nums[i]) == nums[j]) {
                    return new int[]{nums[i], nums[j]};
                }
            }
        }
        return new int[]{};
    }
}
