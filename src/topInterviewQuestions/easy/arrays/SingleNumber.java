package topInterviewQuestions.easy.arrays;

import java.util.HashMap;
import java.util.Map;

public class SingleNumber {
    public static void main(String[] args) {
        System.out.println(singleNumberUsingXOR(new int[]{1, 2, 1, 3, 2}));
    }

    public static int singleNumberUsingMap(int nums[]) {
        Map<Integer, Integer> res = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            res.computeIfPresent(nums[i], (k, v) -> v + 1);
            res.putIfAbsent(nums[i], 1);
        }
        for (Integer key : res.keySet()) {
            if (res.get(key) == 1) {
                return key;
            }
        }
        return 0;
    }

    //XOR solution

    /**
     * a^a=0
     * a^0=a
     * a^b^a = (a^a)^b=0^b=b
     * with the above explanation when we do XOR for the elements,
     * every duplicate element will result as 0 and one single
     * element value will be remaining
     * Ex: 1^2^1^3^2 = (1^1)^(2^2)^3 = (0^0)^3 = 0^3 = 3
     */
    public static int singleNumberUsingXOR(int[] nums) {
        int a = 0;
        for (int i : nums) {
            //1,2,1,3,2
            System.out.print(a + "^" + i);
            a ^= i;
            System.out.println(" is " + a);
        }
        return a;
    }
}
