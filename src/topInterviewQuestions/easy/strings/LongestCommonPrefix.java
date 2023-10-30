package topInterviewQuestions.easy.strings;

public class LongestCommonPrefix {
    public static void main(String[] args) {
        System.out.println(lcpHorizontalScanning(new String[]{"dflower", "flow", "flight"}));
        System.out.println(lcpBinarySearch(new String[]{"flower", "dflow", "flight"}));
    }

    public static String lcpHorizontalScanning(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0]; //flower
        for (int i = 1; i < strs.length; i++) {
            //flow.indexOf("flower") gives -1 as both are not same. If its same just return flow.
            while (strs[i].indexOf(prefix) != 0) {
                //prefix starts with flower and decreases by length 1. example: flower, flowe, flow, flo and fl till it finds
                //exact match
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }

        }
        return prefix;
    }

    public static String lcpBinarySearch(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int minLen = Integer.MAX_VALUE;
        for (String str : strs) {
            minLen = Math.min(minLen, str.length());
        }
        int low = 1;
        int high = minLen;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (isCommonPrefix(strs, middle)) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }


        return strs[0].substring(0, (low + high) / 2);
    }

    private static boolean isCommonPrefix(String[] strs, int len) {
        String str1 = strs[0].substring(0, len);
        for (int i = 1; i < strs.length; i++)
            if (!strs[i].startsWith(str1))
                return false;
        return true;
    }
}
