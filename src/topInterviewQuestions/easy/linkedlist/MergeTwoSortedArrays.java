package topInterviewQuestions.easy.linkedlist;

import topInterviewQuestions.utils.ListNode;

import static topInterviewQuestions.utils.LeetUtils.printLinkedList;

public class MergeTwoSortedArrays {
    public static void main(String[] args) {
        ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        printLinkedList(mergeTwoSortedArrays(list1, list2));

    }

    public static ListNode mergeTwoSortedArrays(ListNode list1, ListNode list2) {


        ListNode dummy = new ListNode();
        ListNode tail = dummy;

        while (list1 != null && list2 != null) {

            if (list1.val < list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;

        }
        if (list1 != null) {
            tail.next = list1;
        } else if (list2 != null) {
            tail.next = list2;
        }
        return dummy.next;
    }
}
