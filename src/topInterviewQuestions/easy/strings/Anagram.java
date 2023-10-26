package topInterviewQuestions.easy.strings;

import java.util.HashMap;
import java.util.Map;

public class Anagram {
    public static void main(String[] args) {
        System.out.println(isValidAnagramFrequencyCounter("anagram", "nagaram"));
        System.out.println(isValidAnagramFrequencyCounter("rat", "car"));
        System.out.println(isValidAnagramFrequencyCounter("ab", "a"));
    }

    public static boolean isValidAnagramFrequencyCounter(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] counter = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counter[s.charAt(i) - 'a']++;
            counter[t.charAt(i) - 'a']--;
        }

        for (int c : counter) {
            if (c != 0) {
                return false;
            }
        }

        return true;

    }

    public static boolean isValidAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> sMap = convertMap(s);
        Map<Character, Integer> tMap = convertMap(t);
        if (sMap.size() != tMap.size()) {
            return false;
        }
        for (Character c : tMap.keySet()) {
            if (sMap.get(c) == null || tMap.get(c) > sMap.get(c)) {
                return false;
            }
        }
        return true;
    }

    private static Map<Character, Integer> convertMap(String s) {
        Map<Character, Integer> strMap = new HashMap<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            strMap.computeIfPresent(c, (k, v) -> v + 1);
            strMap.putIfAbsent(c, 1);
        }
        return strMap;
    }
}
