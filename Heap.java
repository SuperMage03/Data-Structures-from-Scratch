import java.lang.constant.Constable;
import java.util.ArrayList;

public class Heap {
    public static final int MinHeap = 0, MaxHeap = 1;
    private HeapNode root = null;
    private int nodeAmount = 0;
    //Initializer
    private final int mode;
    public Heap(int mode) {this.mode = mode;}

    public int height(int nodes) {return (int) Math.ceil(Math.log(nodes + 1) / Math.log(2)) - 1;}
    public boolean isEmpty() {return nodeAmount == 0;}

    public Constable peak() {
        if (isEmpty()) {return null;}
        else {return root.val;}
    }


    //Adds the element from top to bottom and left to right
    public void enqueue(int val) {
        nodeAmount++;
        if (root == null) {
            root = new HeapNode(val, null, 0);
        }
        else {
            root.addLast(val, height(nodeAmount), 0);
            bubbleUpNode(lastNode());
        }
    }

    private static void removeNodeFromParent(HeapNode node) {
        if (node.parentNode != null) {
            if (node.parentNode.nextLNode == node) {
                node.parentNode.nextLNode = null;
            }
            else {
                node.parentNode.nextRNode = null;
            }
        }
    }

    public void dequeue() {
        if (!isEmpty()) {
            if (nodeAmount - 1 == 0) {
                root = null;
            }
            else {
                HeapNode lastN = lastNode();
                switchVal(root, lastN);
                removeNodeFromParent(lastN);
                bubbleDownNode(root);
            }
            nodeAmount--;
        }
    }

    private HeapNode lastNode() {return root.lastNode(height(nodeAmount), 0);}

    @Override
    public String toString() {
        String rtVal = makeString(root);
        if (!rtVal.isEmpty()) {rtVal = rtVal.substring(2);}
        return "<" + rtVal + ">";
    }

    private String makeString(HeapNode node) {
        if (node == null) {
            return "";
        }
        else {
            return makeString(node.nextLNode) + ", [" + node.val + " " + node.parentNode.val  + "]" + makeString(node.nextRNode);
        }
    }

    public ArrayList<Integer> toList() {return makeList(root);}

    private ArrayList<Integer> makeList(HeapNode node) {
        if (node == null) {
            return new ArrayList<Integer>();
        }
        else {
            ArrayList<Integer> allList = new ArrayList<Integer>();
            allList.addAll(makeList(node.nextLNode));
            allList.add(node.val);
            allList.addAll(makeList(node.nextRNode));
            return allList;
        }
    }




    private void switchVal(HeapNode HN1, HeapNode HN2) {
        int HN1Val = HN1.val;
        HN1.val = HN2.val;
        HN2.val = HN1Val;
    }

    private void bubbleUpNode(HeapNode node) {
        if (mode == MinHeap) {
            if (node.parentNode != null && node.val < node.parentNode.val) {
                switchVal(node, node.parentNode);
                bubbleUpNode(node.parentNode);
            }
        }

        else {
            if (node.parentNode != null && node.val > node.parentNode.val) {
                switchVal(node, node.parentNode);
                bubbleUpNode(node.parentNode);
            }
        }
    }


    private void bubbleDownNode(HeapNode node) {
        if (mode == MinHeap) {
            if (node.nextLNode != null || node.nextRNode != null) {
                int LNodeVal = node.nextLNode == null ? Integer.MAX_VALUE : node.nextLNode.val;
                int RNodeVal = node.nextRNode == null ? Integer.MAX_VALUE : node.nextRNode.val;
                if (node.val > Math.max(LNodeVal, RNodeVal)) {
                    if (LNodeVal < RNodeVal) {
                        switchVal(node, node.nextLNode);
                        bubbleDownNode(node.nextLNode);
                    }
                    else {
                        switchVal(node, node.nextRNode);
                        bubbleDownNode(node.nextRNode);
                    }
                }
                else if (node.val > LNodeVal) {
                    switchVal(node, node.nextLNode);
                    bubbleDownNode(node.nextLNode);
                }
                else if (node.val > RNodeVal) {
                    switchVal(node, node.nextRNode);
                    bubbleDownNode(node.nextRNode);
                }
            }
        }

        else {
            if (node.nextLNode != null || node.nextRNode != null) {
                int LNodeVal = node.nextLNode == null ? Integer.MIN_VALUE : node.nextLNode.val;
                int RNodeVal = node.nextRNode == null ? Integer.MIN_VALUE : node.nextRNode.val;
                if (node.val < Math.min(LNodeVal, RNodeVal)) {
                    if (LNodeVal > RNodeVal) {
                        switchVal(node, node.nextLNode);
                        bubbleDownNode(node.nextLNode);
                    }
                    else {
                        switchVal(node, node.nextRNode);
                        bubbleDownNode(node.nextRNode);
                    }
                }
                else if (node.val < LNodeVal) {
                    switchVal(node, node.nextLNode);
                    bubbleDownNode(node.nextLNode);
                }
                else if (node.val < RNodeVal) {
                    switchVal(node, node.nextRNode);
                    bubbleDownNode(node.nextRNode);
                }
            }
        }
    }
}

class HeapNode {
    public int val, hPos;
    public HeapNode nextLNode = null, nextRNode = null, parentNode;

    public HeapNode(int val, HeapNode parentNode, int hPos) {
        this.val = val;
        this.parentNode = parentNode;
        this.hPos = hPos;
    }

    public boolean addLast(int val, int height, int curHeight) {
        if (curHeight == height - 1) {
            if (nextLNode == null) {
                nextLNode = new HeapNode(val, this, height);
                return true;
            }
            else if (nextRNode == null) {
                nextRNode = new HeapNode(val, this, height);
                return true;
            }
            else {return false;}
        }
        else {
            if (!nextLNode.addLast(val, height, curHeight + 1)) {
                return nextRNode.addLast(val, height, curHeight + 1);
            }
            return true;
        }
    }


    public HeapNode lastNode(int height, int curHeight) {
        if (curHeight == height - 1) {
            if (nextRNode != null) {
                return nextRNode;
            }
            else if (nextLNode != null) {
                return nextLNode;
            }
            else {return null;}
        }
        else {
            HeapNode foundNode = nextRNode.lastNode(height, curHeight + 1);
            if (foundNode == null) {
                return nextLNode.lastNode(height, curHeight + 1);
            }
            return foundNode;
        }
    }
}
