package facebook;

public class SparseVector {

    SparseVector(int[] nums) {

    }

    public static void main(String[] args) {
        SparseVector v1 = new SparseVector(new int[]{1,0,0,2,3});
        SparseVector v2 = new SparseVector(new int[]{0,3,0,4,0});
        int ans  = v1.dotProduct(v2);
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {

        return 0;

    }
}
