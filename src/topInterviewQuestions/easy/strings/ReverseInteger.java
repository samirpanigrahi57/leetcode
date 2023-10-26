package topInterviewQuestions.easy.strings;

public class ReverseInteger {
    public static void main(String[] args) {
        System.out.println(reverseInteger(-123));
        System.out.println(reverseInteger(123));
        System.out.println(reverseInteger(1534236469));

    }

    public static int reverseInteger(int num) {
        if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
            return 0;
        }

        long res = 0;

        while (num != 0) {
            res = (res * 10) + (num % 10);
            num /= 10;

        }
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) res;
    }
}
