package topInterviewQuestions.easy.linkedlist;

import topInterviewQuestions.utils.ListNode;

import static topInterviewQuestions.utils.LeetUtils.printLinkedList;

public class RemoveNthNode {


    public static void main(String[] args) {

        ListNode node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        printLinkedList(removeNthFromEndTwoPass(node, 2));
        ListNode node1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        printLinkedList(removeNthFromEndOnePass(node1, 2));

    }

    public static ListNode removeNthFromEndTwoPass(ListNode head, int n) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int length = 0;
        ListNode first = head;
        //to find length of node
        while (first != null) {
            length++;
            first = first.next;
        }
        //5-2 = 3
        length = length - n;
        first = dummy;
        while (length > 0) {
            length--;
            first = first.next;
        }
        //The values of every ListNode corresponds to single reference.
        // So here we are deleting the reference where value = 4. It will delete in dummy node as well
        first.next = first.next.next;
        return dummy.next;
    }

    public static ListNode removeNthFromEndOnePass(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }

        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;


    }

}
