package facebook;

import java.util.Stack;

/**
 * 1. addition and substraction has least priority
 * multiplication and division has highest priority
 * when + and - operator is fetched store them in stack like 5 or (-1)*5
 * when / or * has received pop an element from stack and perform / or * with the element and calculated num and then store in stack
 * iterate stack and calculate sum
 **/
public class BasicCalculatorII {
    public static void main(String[] args) {
        System.out.println(calculate("3/2"));
    }

    private static int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        Character operator = '+';

        for (int i = 0; i < s.length(); i++) {
            //build the numbers like 12,50,645
            if (Character.isDigit(s.charAt(i))) {
                num = num * 10 + (s.charAt(i) - '0');
            }

            //iterate when !isDigit, space and especially the last character
            if ((!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ') || i == s.length() - 1) {
                if (operator == '+') {
                    stack.push(num);
                }
                if (operator == '-') {
                    stack.push((-1) * num);
                }
                if (operator == '*') {
                    stack.push(num * stack.pop());
                }
                //pop the number from stack and divide it with calculated num
                if (operator == '/') {
                    stack.push(stack.pop() / num);
                }
                operator = s.charAt(i);
                num = 0;

            }
        }
        int sum = 0;
        //calculate the sum of numbers in stack
        for (Integer st : stack) {
            sum = sum + st;

        }

        return sum;
    }
}
