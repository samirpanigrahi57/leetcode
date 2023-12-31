package topInterviewQuestions.easy.arrays;

import static topInterviewQuestions.utils.LeetUtils.printIntArray;

public class RotateArray {
    public static void main(String[] args) {
        //int[] res = rotateArray(new int[]{1,2,3,4,5,6,7}, 8);
        //printArray(res);
        System.out.println();
         //int[] res = reverseApproach(new int[]{-1,-100,3,99}, 1);
         printIntArray(rotate(new int[]{-1,-100,3,99}, 2));
        //printArray(res);
    }

    public static int[] reverseApproach(int[] nums, int k){
        k = k % nums.length;
        System.out.println("k value after % is "+k);
        reverse(nums,0,nums.length-1);
        printIntArray(nums);
        reverse(nums, 0, k-1);
        printIntArray(nums);
        reverse(nums, k, nums.length-1);
        printIntArray(nums);
        return nums;
    }

    public static void reverse(int[] nums, int start, int end){
        while(start < end){
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }


    //Using an extra array
    public static int[] rotate(int[] nums, int k){
        int a[] = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            System.out.println((i+k) % nums.length);
            //(i+k) % nums.length this will always <= nums.length - 1.
            // It will start with indexes after Kth position
            a[(i+k) % nums.length] = nums[i];

        }
        for(int i = 0; i < nums.length; i++){
            nums[i] = a[i];
        }
        return nums;

    }

    //This will fail if k > bums.length
    public static int[] rotateArray(int[] arr, int k){
        int[] res = new int[arr.length];
        int n = 0;
        for(int i = arr.length-k; i < arr.length; i++){
            res[n] = arr[i];
            n++;
        }
        for(int i = 0; i < arr.length-k;  i++){
            res[n] = arr[i];
            n++;
        }

        arr = res;

        return arr;
    }
}
