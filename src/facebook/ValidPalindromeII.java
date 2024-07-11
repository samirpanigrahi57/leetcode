package facebook;

public class ValidPalindromeII {
    public static void main(String[] args) {

        System.out.println(validPalindrome("abca"));

    }

    static boolean validPalindrome(String s) {
        int slow = 0;
        int fast = s.length() - 1;
        while (slow < fast) {
            if(s.charAt(slow) != s.charAt(fast)){
                return checkPalindrome(s, slow+1, fast) || checkPalindrome(s, slow, fast-1);
            }
            slow++;
            fast--;

        }
        return true;

    }

    private static boolean checkPalindrome(String s, int i, int j) {
        while(i < j){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
