package topInterviewQuestions.easy.strings;

import java.util.*;

public class FirstUniqueCharacter {
    public static void main(String[] args) {
        System.out.println(firstUniqueCharacter("leetcode"));
        System.out.println(firstUniqueCharacter("loveleetcode"));
        System.out.println(firstUniqueCharacter("aabb"));
    }

    public static int firstUniqueCharacter(String s) {
        Map<Character, Integer> res = new HashMap<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            res.put(chars[i], res.getOrDefault(chars[i], 0) + 1);
        }

        for (int i = 0; i < chars.length; i++) {
            if (res.get(chars[i]) == 1) {
                return i;
            }
        }

        return -1;

    }


}
