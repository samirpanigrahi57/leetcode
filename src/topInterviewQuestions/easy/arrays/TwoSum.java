package topInterviewQuestions.easy.arrays;

import topInterviewQuestions.utils.LeetUtils;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args) {
        LeetUtils.printIntArray(twoSumBruteForce(new int[]{2, 7, 11, 15}, 18));
        LeetUtils.printIntArray(twoSumOnePass(new int[]{2, 7, 11, 15}, 18));
    }

    public static int[] twoSumOnePass(int[] nums, int target) {
        Map<Integer, Integer> twoSum = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            //calculate the diff
            int diff = target - nums[i];
            //check if the diff already exists in the map.
            // If yes return current element and diff (here diff will be an element iterated previously)
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
