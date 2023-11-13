package topInterviewQuestions.easy.math;

import java.util.HashMap;
import java.util.Map;

public class RomanToInteger {
    public static void main(String[] args) {
        System.out.println(romanToInt("MCMXCIV"));

    }

    public static int romanToInt(String s) {

        Map<String, Integer> romans = new HashMap<>();
        romans.put("I", 1);
        romans.put("V", 5);
        romans.put("X", 10);
        romans.put("L", 50);
        romans.put("C", 100);
        romans.put("D", 500);
        romans.put("M", 1000);

        int sum = 0;
        int i = 0;
        while (i < s.length()) {
            String symbol = s.substring(i, i + 1);
            int currentValue = romans.get(symbol);
            int nextValue = 0;
            if (i + 1 < s.length()) {
                symbol = s.substring(i + 1, i + 2);
                nextValue = romans.get(symbol);
            }
            if (currentValue < nextValue) {
                sum += (nextValue - currentValue);
                i = i + 2;
            } else {
                sum += currentValue;
                i = i + 1;
            }
        }

        return sum;

    }
}
