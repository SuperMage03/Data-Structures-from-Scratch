public class SegmentTreeV2 {
    public static int MIN = 0, MAX = 1;
    private final int mode;
    private final int[] arr;
    private STNodeV2 root;
    private void buildTree(STNodeV2 node, int curL, int curR) {
        node.left = curL;
        node.right = curR;
        if (curL == curR) {
            node.val = arr[curL];
        }
        else {
            int mid = (curL + curR) / 2;
            node.nextLNode = new STNodeV2();
            node.nextRNode = new STNodeV2();
            buildTree(node.nextLNode, curL, mid);
            buildTree(node.nextRNode, mid + 1, curR);
            if (mode == 0) {
                node.val = Math.min(node.nextLNode.val, node.nextRNode.val);
            }
            else {
                node.val = Math.max(node.nextLNode.val, node.nextRNode.val);
            }
        }
    }
    public SegmentTreeV2(int[] arr, int mode) {
        this.arr = arr;
        this.mode = mode;
        root = new STNodeV2();
        buildTree(root, 0, arr.length-1);
    }

    public int query(int QL, int QR) {return query(root, QL, QR);}

    private int query(STNodeV2 node, int QL, int QR) {
        int curL = node.left, curR = node.right;
        //Case 1 [L, R] is outside [QL, QR], Return infinity
        if (curR < QL || QR < curL) {
            return mode == 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        //Case 2 [L, R] is inside [QL, QR], Return node val
        else if (QL <= curL && QR >= curR) {
            return node.val;
        }
        //Case 3 [L, R] is partially inside [QL, QR], Keep branching until inside
        else {
            int queriedL = query(node.nextLNode, QL, QR); //Left
            int queriedR = query(node.nextRNode, QL, QR); //Right
            return mode == 0 ? Math.min(queriedL, queriedR) : Math.max(queriedL, queriedR);
        }
    }

    public void update(int index, int newVal) {update(root, index, newVal);}

    private void update(STNodeV2 curNode, int index, int newVal) {
        int L = curNode.left, R = curNode.right;
        //Case 1: index is found
        if (L == R && L == index) {
            curNode.val = newVal;
        }
        //Case 2: index is partially within [L, R]
        else if (L <= index && index <= R) {
            update(curNode.nextLNode, index, newVal);
            update(curNode.nextRNode, index, newVal);
            if (mode == 0) {
                curNode.val = Math.min(curNode.nextLNode.val, curNode.nextRNode.val);
            }
            else {
                curNode.val = Math.max(curNode.nextLNode.val, curNode.nextRNode.val);
            }
        }
    }
}

class STNodeV2 {
    public int val, left, right;
    public STNodeV2 nextLNode = null, nextRNode = null;
}
