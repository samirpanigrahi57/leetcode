package topInterviewQuestions.easy.strings;

public class StrStr {

    /**
     *
     * Look at the solution
     * */
    public static void main(String[] args) {
        //System.out.println(strStr("sadbutsad", "sad"));
        //System.out.println(strStr("leetcode", "leeto"));
        System.out.println(strStr("mississippi" ,"issipi"));

    }

    // This is sliding window approach
    public static int strStr(String haystack, String needle) {
        if (needle.length() > haystack.length()) {
            return -1;
        }
        char[] hay = haystack.toLowerCase().toCharArray();
        for (int i = 0; i < hay.length; i++) {
            if (checkNeedleSubString(hay, needle, i)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean checkNeedleSubString(char[] hay, String needle, int i) {
        if(hay.length < i + needle.length()){
            return false;
        }
        int k = 0;
        int h = i;
        while (k < needle.length()) {
            if (hay[h] != needle.charAt(k)) {
                return false;
            }
            k++;
            h++;
        }
        return true;
    }
}
