public class SegmentTree {
    public static int MIN = 0, MAX = 1;
    private final int mode;
    private final int[] arr;
    private final STNode[] SegTree;

    private void buildTree(int index, int curL, int curR) {
        SegTree[index].left = curL;
        SegTree[index].right = curR;
        if (curL == curR) {
            SegTree[index].val = arr[curL];
        }
        else {
            int mid = (curL + curR) / 2;
            buildTree(index * 2, curL, mid);
            buildTree(index * 2 + 1, mid + 1, curR);
            if (mode == 0) {
                SegTree[index].val = Math.min(SegTree[index * 2].val, SegTree[index * 2 + 1].val);
            }
            else {
                SegTree[index].val = Math.max(SegTree[index * 2].val, SegTree[index * 2 + 1].val);
            }
        }
    }

    public SegmentTree(int[] arr, int mode) {
        this.mode = mode;
        this.arr = arr;
        SegTree = new STNode[4 * arr.length];
        for (int i = 0; i < SegTree.length; i++) {
            SegTree[i] = new STNode();
        }
        buildTree(1, 0, arr.length - 1);
    }

    public int query(int QL, int QR) {return query(1, QL, QR);}

    private int query(int index, int QL, int QR) {
        int curL = SegTree[index].left, curR = SegTree[index].right;
        //Case 1 [L, R] is outside [QL, QR], Return infinity
        if (curR < QL || QR < curL) {
            return mode == 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        //Case 2 [L, R] is inside [QL, QR], Return node val
        else if (QL <= curL && QR >= curR) {
            return SegTree[index].val;
        }
        //Case 3 [L, R] is partially inside [QL, QR], Keep branching until inside
        else {
            int queriedL = query(index * 2, QL, QR); //Left
            int queriedR = query(index * 2 + 1, QL, QR); //Right
            return mode == 0 ? Math.min(queriedL, queriedR) : Math.max(queriedL, queriedR);
        }
    }

    public void update(int index, int newVal) {update(1, index, newVal);}

    private void update(int curIdx, int index, int newVal) {
        int L = SegTree[curIdx].left, R = SegTree[curIdx].right;
        //Case 1: index is found
        if (L == R && L == index) {
            SegTree[curIdx].val = newVal;
        }
        //Case 2: index is partially within [L, R]
        else if (L <= index && index <= R) {
            update(curIdx * 2, index, newVal);
            update(curIdx * 2 + 1, index, newVal);
            if (mode == 0) {
                SegTree[curIdx].val = Math.min(SegTree[curIdx * 2].val, SegTree[curIdx * 2 + 1].val);
            }
            else {
                SegTree[curIdx].val = Math.max(SegTree[curIdx * 2].val, SegTree[curIdx * 2 + 1].val);
            }
        }
    }
}

class STNode {public int val, left, right;}
