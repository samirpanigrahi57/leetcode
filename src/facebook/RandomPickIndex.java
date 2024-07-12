package facebook;

import java.util.*;

public class RandomPickIndex {

    Map<Integer, List<Integer>> sol = new HashMap<>();

    public RandomPickIndex(int[] nums) {
        for(int i = 0; i < nums.length; i++){
            if(sol.containsKey(nums[i])){
                List<Integer> list = sol.get(nums[i]);
                list.add(i);
            }else{
                sol.putIfAbsent(nums[i], new ArrayList<>(Arrays.asList(i)));
            }

        }

    }
    public static void main(String[] args) {
        RandomPickIndex obj = new RandomPickIndex(new int[]{1, 2, 3, 3, 3});
        System.out.println(obj.pick(2));

    }

    public int pick(int target) {

        List<Integer> list = sol.get(target);
        int i = (int)(Math.random()*list.size());
        return list.get(i);

        /*List<Integer> num = sol.get(target);
        Random random = new Random();
        if(num.size() == 1){
            return num.get(0);
        }else{
            return random.ints(num.get(0),num.get(num.size() - 1)).findAny().getAsInt();
        }*/



    }
}
