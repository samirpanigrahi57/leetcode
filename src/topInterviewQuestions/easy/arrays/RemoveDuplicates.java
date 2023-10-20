package topInterviewQuestions.easy.arrays;

public class RemoveDuplicates {
    public static void main(String[] args) {

       removeDuplicates(new int[]{1,1,2,2,2,3,4,5});
        System.out.println(removeDups(new int[]{1,1,2,2,2,3,3,4,5}));

    }

    public static int removeDups(int[] nums){
        int uniqueIndex = 1;
        for(int i = 1; i < nums.length; i ++){
            if(nums[i-1] != nums[i]){
                nums[uniqueIndex] = nums[i];
                uniqueIndex++;
            }
        }

        return uniqueIndex;
    }

    public static int removeDuplicates(int[] nums) {
        int visitedElement = nums[0];
        int[] res = new int[nums.length];
        int counter = 0;
        int duplicates = 0;
        res[0] = nums[0];
        for(int i = 1; i < nums.length; i++){
            if(visitedElement != nums[i]){
                counter ++;
                res[counter] = nums[i];
                visitedElement = nums[i];
            }else{
                duplicates ++;
            }
        }
        nums = res;
        return counter+1;
    }
}
