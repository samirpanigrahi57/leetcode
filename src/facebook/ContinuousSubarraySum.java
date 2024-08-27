package facebook;

import java.util.HashMap;
import java.util.Map;

public class ContinuousSubarraySum {
    public static void main(String[] args) {
        System.out.println(checkSubarraySum(new int[]{23,2,4,6,7},6));

    }

    public static boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> remainders = new HashMap<>();
        remainders.put(0, -1);
        int total = 0;
        for(int i = 0; i < nums.length; i++){
            total += nums[i];
            int r = total%k;

            if(!remainders.containsKey(r)){
                remainders.put(r, i);
            }else if(i - remainders.get(r) > 1){// this case is to skip single values that matches multiples of k
                return true;
            }

        }
        return false;

    }
}
