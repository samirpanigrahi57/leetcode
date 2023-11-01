package topInterviewQuestions.utils;

public class LeetUtils {

    public static void printIntArray(int[] res) {
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i] + ",");
        }
        System.out.println();
    }

    public static void printCharArray(char[] res) {
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i] + ",");
        }
        System.out.println();
    }

    public static void printLinkedList(ListNode node) {
        while(node != null){
            System.out.print(node.val+"->");
            node = node.next;
        }
        System.out.println(node);
    }
}
