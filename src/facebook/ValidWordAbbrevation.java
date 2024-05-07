package facebook;

public class ValidWordAbbrevation {
    public static void main(String[] args) {
        String word = "internationalization";
        String abbr = "i5a11o1";

        System.out.println(validWordAbbreviation(word, abbr));
    }

    private static boolean validWordAbbreviation(String word, String abbr) {

        //if any of the words in null return false
        if (word == null || abbr == null) return false;

        int wordLength = 0;
        int abbrLength = 0;

        //iterate till any one of the last element. if its a valid abbr both
        // the length should be at last position which is our return condition
        while (wordLength < word.length() && abbrLength < abbr.length()) {
            //find the first digit in abbr
            if (Character.isDigit(abbr.charAt(abbrLength))) {
                //if the first digit is zero return false as leading zero is not accepted
                if (abbr.charAt(abbrLength) == '0') {
                    return false;
                }
                int total = 0;
                //iterate again to calculate the sum of digits like 55,10,22
                do {
                    // - '0' is to minus ASCII
                    total = total * 10 + abbr.charAt(abbrLength) - '0';
                    //increment only abbr as we are calculating digit sum
                    abbrLength++;

                } while (abbrLength < abbr.length() && Character.isDigit(abbr.charAt(abbrLength)));

                wordLength += total;
            } else {
                //if not digit then check id chars match
                if (word.charAt(wordLength) != abbr.charAt(abbrLength)) return false;
                abbrLength++;
                wordLength++;
            }
        }
        //check if we are iterated completely
        return wordLength == word.length() && abbrLength == abbr.length();
    }
}
