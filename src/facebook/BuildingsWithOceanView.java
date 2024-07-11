package facebook;

import topInterviewQuestions.utils.LeetUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class BuildingsWithOceanView {
    public static void main(String[] args) {
        int[] res = findBuildings1(new int[]{4,2,3,1});
        LeetUtils.printIntArray(res);
    }

    private static int[] findBuildings(int[] heights) {

        int heighestFloor = 0;
        Set<Integer> result = new TreeSet<>();
        //int[] res = new int[heights.length];

        for(int i = heights.length - 1; i >= 0; i--){
            if(heights[i] > heighestFloor){
                heighestFloor = heights[i];
                result.add(i);
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }
//4,2,3,1
    private static int[] findBuildings1(int[] heights) {
        int max = 0;
        int n=heights.length; //4
        int idx=n-1;//3
        for (int i=n-1; i>=0; i--) {
            int cur = heights[i];
            if (cur>max) {
                max = cur;
                //replaces value with index as it has oceanview and decreases the index
                heights[idx--]=i;
            }
        }
        //heights[4,0,2,3]
        //copies heights array from index+1 as it decreases in last step
        return Arrays.copyOfRange(heights, idx+1,n);
    }
}
