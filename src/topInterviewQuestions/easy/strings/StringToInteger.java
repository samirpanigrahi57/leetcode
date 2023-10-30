package topInterviewQuestions.easy.strings;

public class StringToInteger {
    public static void main(String[] args) {
        //System.out.println(stringToInt("words and 987"));
        //System.out.println(stringToInt("  -42 with words"));
    }

    /*public static int stringToInt(String s) {

        StringBuilder sb = new StringBuilder();
        s.chars().filter(c -> (Character.isDigit(c) || c == '-' || c == '+')).mapToObj(c -> Character.toLowerCase((char) c)).forEach(sb::append);

        return Integer.parseInt(sb.toString());
    }*/
}
