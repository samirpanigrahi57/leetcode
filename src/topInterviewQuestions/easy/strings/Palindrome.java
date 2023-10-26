package topInterviewQuestions.easy.strings;

public class Palindrome {
    public static void main(String[] args) {
        System.out.println(isValidPallindrome("A man, a plan, a canal: Panama"));
        System.out.println(isValidPallindrome("race a car"));
    }

    public static boolean isValidPallindrome(String s) {

        StringBuilder sb = new StringBuilder();
        s.chars()
                .filter(c -> Character.isLetterOrDigit(c))
                .mapToObj(c -> Character.toLowerCase((char)c))
                .forEach(sb::append);


        return sb.toString().equals(sb.reverse().toString());
    }
}
