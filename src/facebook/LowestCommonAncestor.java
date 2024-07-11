package facebook;

public class LowestCommonAncestor {

    public static void main(String[] args) {

    }

    public static Node lowestCommonAncestor(Node p, Node q) {
        Node pdummy = p;
        Node qdummy = q;
        while (pdummy != qdummy) {
            pdummy = pdummy == null ? q : pdummy.parent;
            qdummy = qdummy == null ? p : qdummy.parent;

        }
        return pdummy;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }

    ;
}
