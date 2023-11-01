package topInterviewQuestions;

import topInterviewQuestions.easy.linkedlist.ReverseLinkedList;

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

    public static void printLinkedList(ReverseLinkedList.ListNode node) {
        while(node != null){
            System.out.println(node.val);
            node = node.next;
        }
    }
}
