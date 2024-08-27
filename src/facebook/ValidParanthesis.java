package facebook;

import java.util.Stack;

public class ValidParanthesis {
    public static void main(String[] args) {
        System.out.println(isValid("]"));

    }


    public static boolean isValid(String s) {
        Stack<Character> stk = new Stack();
        char[] chars = s.toCharArray();
        for (Character c : chars) {
            if (c == '(' || c == '{' || c == '[') {
                stk.push(c);
            } else if (c == ')') {
                if (!stk.isEmpty() && stk.peek() == '(') {
                    stk.pop();
                } else {
                    //stk.push(c);
                    return false;
                }
            } else if (c == '}') {
                if (!stk.isEmpty() && stk.peek() == '{') {
                    stk.pop();
                } else {
                    //stk.push(c);
                    return false;
                }
            } else if (c == ']') {
                if (!stk.isEmpty() && stk.peek() == '[') {
                    stk.pop();
                } else {
                    //stk.push(c);
                    return false;
                }
            }
        }


        return stk.size() > 0 ? false : true;

    }
}
