package topInterviewQuestions.easy.strings;

import topInterviewQuestions.utils.LeetUtils;

public class ReverseString {

    public static void main(String[] args) {
        LeetUtils.printCharArray(reverseString(new char[]{'h','e','l','l','o'}));
        LeetUtils.printCharArray(reverseString(new char[]{'H','a','n','n','a','h'}));
    }
    public static char[] reverseString(char[] s){
        int end = s.length - 1;
        for(int start = 0; start < s.length/2; start++){
            char temp = s[start];
            s[start] = s[end];
            s[end] = temp;
            end--;
        }
        return s;
    }
}
