package topInterviewQuestions.easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicates {
    public static void main(String[] args) {
        System.out.println(containsDuplicates(new int[]{1, 2, 3, 4}));
        System.out.println(containsDuplicatesWithHash(new int[]{1, 2, 3, 4}));
    }

    public static boolean containsDuplicates(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsDuplicatesWithHash(int[] nums) {
        Set<Integer> res = new HashSet<>();
        for (int n : nums) {
            if (res.contains(n)) {
                return true;
            }
            res.add(n);
        }
        return false;
    }
}
