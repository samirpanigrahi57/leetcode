package facebook;

public class FirstAndLastPosition {
    public static void main(String[] args) {

        int[] res = searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println(res[0]+","+res[1]);

    }

    public static int[] searchRange(int[] nums, int target) {

        int left = bfs(nums, target, true);
        int right = bfs(nums, target, false);
        return new int[]{left, right};

    }

    public static int bfs(int[] nums, int target, boolean leftBiased) {

        int left = 0;
        int right = nums.length - 1;
        int index = -1;

        while (left <= right) {
            int middle = (left + right) / 2;
            if (target > nums[middle]) {
                left = middle + 1;
            } else if (target < nums[middle]) {
                right = middle - 1;
            } else {
                index = middle;
                if (leftBiased) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            }


        }

        return index;

    }
}
