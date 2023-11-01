package topInterviewQuestions.easy.linkedlist;

import topInterviewQuestions.utils.ListNode;

public class PalindromeLinkedList {
    public static void main(String[] args) {

        ListNode validPalindrome = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(2,new ListNode(1)))));
        System.out.println(isValidPalindrome(validPalindrome));
        ListNode inValid = new ListNode(1, new ListNode(2));
        System.out.println(isValidPalindrome(inValid));

    }

    private static boolean isValidPalindrome(ListNode head) {
        if (head == null) return true;
        ListNode middle = middle(head);
        ListNode last = reverse(middle);
        ListNode current = head;
        while (last != null) {
            if (last.val != current.val) {
                return false;
            }
            last = last.next;
            current = current.next;
        }
        return true;
    }

    private static ListNode reverse(ListNode head) {
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

    //using fast pointer concept
    private static ListNode middle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;

    }
}
