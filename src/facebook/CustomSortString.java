package facebook;

import java.util.HashMap;
import java.util.Map;

public class CustomSortString {
    public static void main(String[] args) {
        System.out.println(customSortString("cba","abcd"));

    }

    public static String customSortString(String order, String s) {

        Map<Character,Integer> dict = new HashMap<>();
        for(int i = 0; i < s.length(); i++){
            dict.computeIfPresent(s.charAt(i), (k,v) -> v+1);
            dict.putIfAbsent(s.charAt(i), 1);
        }
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < order.length(); j++){
            int count = dict.getOrDefault(order.charAt(j), 0);
            if(count > 0){
                while(count > 0){
                    sb.append(order.charAt(j));
                    count--;
                }
                dict.remove(order.charAt(j));
            }

        }
        dict.forEach((k,v) -> {
            int count = v;
            while(count > 0){
                sb.append(k);
                count--;
            }

        });
        return sb.toString();

    }
}
