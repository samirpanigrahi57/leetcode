package topInterviewQuestions.easy.arrays;

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
        //iterate from second element to last element
        for (int i = 1; i < nums.length; i++) {
            //check if two consecutive elements are same as it is sorted
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsDuplicatesWithHash(int[] nums) {
        Set<Integer> res = new HashSet<>();
        //store in set and check if the set already contains element before storing it
        for (int n : nums) {
            if (res.contains(n)) {
                return true;
            }
            res.add(n);
        }
        return false;
    }
}
