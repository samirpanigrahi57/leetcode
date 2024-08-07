package facebook;

public class KthMissingPositiveNumber {
    public static void main(String[] args) {
        System.out.println(findKthPositive(new int[]{2, 3, 4, 7, 11}, 5));

    }

    private static int findKthPositive(int[] arr, int k) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int pivot = left + (right - left) / 2;
            // If number of positive integers
            // which are missing before arr[pivot]
            // is less than k -->
            // continue to search on the right.
            if (arr[pivot] - pivot - 1 < k) {// 4 - 2 - 1 < 5 = 1 < 5
                left = pivot + 1;
                // Otherwise, go left.
            } else {
                right = pivot - 1;
            }
        }

        // At the end of the loop, left = right + 1,
        // and the kth missing is in-between arr[right] and arr[left].
        // The number of integers missing before arr[right] is
        // arr[right] - right - 1 -->
        // the number to return is
        // arr[right] + k - (arr[right] - right - 1) = k + left
        return left + k;
    }

    public int findKthPositiveBruteForce(int[] arr, int k) {
        // if the kth missing is less than arr[0]
        if (k <= arr[0] - 1) {
            return k;
        }
        k -= arr[0] - 1;

        // search kth missing between the array numbers
        int n = arr.length;
        for (int i = 0; i < n - 1; ++i) {
            // missing between arr[i] and arr[i + 1]
            int currMissing = arr[i + 1] - arr[i] - 1;
            // if the kth missing is between
            // arr[i] and arr[i + 1] -> return it
            if (k <= currMissing) {
                return arr[i] + k;
            }
            // otherwise, proceed further
            k -= currMissing;
        }

        // if the missing number if greater than arr[n - 1]
        return arr[n - 1] + k;
    }
}
