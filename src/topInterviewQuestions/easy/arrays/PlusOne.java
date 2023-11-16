package topInterviewQuestions.easy.arrays;

import static topInterviewQuestions.utils.LeetUtils.printIntArray;

public class PlusOne {

    public static void main(String[] args) {
        printIntArray(plusOne(new int[]{9, 9, 9, 9}));
        printIntArray(plusOne(new int[]{9, 1, 9, 9}));

    }


    public static int[] plusOne(int[] digits) {

        //iterate from end
        for (int index = digits.length - 1; index >= 0; index--) {
            //if the digit is 9 then replace with 0
            if (digits[index] == 9) {
                digits[index] = 0;
            } else {
                //if the digit is not 9 then increment by 1 and return
                //digits[index] = digits[index]+1;
                digits[index]++;
                return digits;
            }
        }
//if the program executes below lines then it means all the digits are 9.
// Increase the length of the array by 1 and assign digits[0] = 1. Ex: 999 -> 1000
        digits = new int[digits.length + 1];
        digits[0] = 1;

        return digits;
    }
}
