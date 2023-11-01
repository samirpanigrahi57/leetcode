package topInterviewQuestions.easy.linkedlist;

import topInterviewQuestions.LeetUtils;

//https://www.youtube.com/watch?v=ugQ2DVJJroc
public class ReverseLinkedList {

    /*public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }*/

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        LeetUtils.printLinkedList(reverseLinkedListIterative(head));
        ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        LeetUtils.printLinkedList(reverseLinkedListRecursion(head1));

    }

    public static ListNode reverseLinkedListIterative(ListNode head) {
        ListNode current = head;
        ListNode previous = null;
        while (current != null) {
            ListNode temp = current.next;
            current.next = previous;
            previous = current;
            current = temp;
        }
        return previous;
    }

    //TODO: revisit again
    public static ListNode reverseLinkedListRecursion(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseLinkedListRecursion(head.next);
        ListNode headNext = head.next;
        headNext.next = head;
        head.next = null;
        return newHead;
    }
}
