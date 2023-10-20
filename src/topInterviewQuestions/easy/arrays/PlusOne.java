package topInterviewQuestions.easy.arrays;

import static topInterviewQuestions.LeetUtils.printArray;

public class PlusOne {

    public static void main(String[] args) {
        printArray(plusOne(new int[]{9, 9, 9, 9}));

    }



    public static int[] plusOne(int[] digits) {

        for (int index = digits.length - 1; index >= 0; index--) {
            if (digits[index] == 9) {
                digits[index] = 0;
            } else {
                //digits[index] = digits[index]+1;
                digits[index]++;
                return digits;
            }
        }

        digits = new int[digits.length + 1];
        digits[0] = 1;

        return digits;
    }
}
