package facebook;

public class MinimumRemoveToMakeValidParentheses {

    public static void main(String[] args) {

        System.out.println(minRemoveToMakeValid("lee(t(c)o)de)"));
        System.out.println(minRemoveToMakeValid("))(("));
    }

    private static String minRemoveToMakeValid(String s) {

        StringBuilder sb = new StringBuilder();
        int open = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                open++;
            }
            if (s.charAt(i) == ')') {
                if (open == 0) {
                    continue;
                }
                open--;
            }
            sb.append(s.charAt(i));
        }

        StringBuilder result = new StringBuilder();
        for (int i = sb.length() - 1; i >= 0; i--) {
            if (sb.charAt(i) == '(' && open-- > 0) {
                continue;
            }
            result.append(sb.charAt(i));
        }
        return result.reverse().toString();
    }
}
