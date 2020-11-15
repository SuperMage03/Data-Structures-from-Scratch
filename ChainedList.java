import java.util.HashSet;

public class ChainedList {
    private CLNode head, tail;
    public ChainedList() {
        head = null; tail = null;
    }
    //Stack
    public void push(int addVal) {
        if (head == null) {
            head = new CLNode(addVal, null, null);
            tail = head;
        }
        else {
            tail = new CLNode(addVal, tail, null);
            tail.getLeft().setRight(tail);
        }
    }
    public int pop() {
        CLNode rtVal = tail;
        if (head != null) {
            if (tail.getLeft() == null) {
                head = null; tail = null;
            }
            else {
                tail = tail.getLeft();
                tail.setRight(null);
            }
        }
        return rtVal.getVal();
    }
    //Queue
    public void enqueue(int addVal) {
        if (head == null) {
            head = new CLNode(addVal, null, null);
            tail = head;
        }
        else {
            head = new CLNode(addVal, null, head);
            head.getRight().setLeft(head);
        }
    }

    public int dequeue() {
        CLNode rtVal = head;
        if (tail != null) {
            if (head.getRight() == null) {
                head = null; tail = null;
            }
            else {
                head = head.getRight();
                head.setLeft(null);
            }
        }
        return rtVal.getVal();
    }
    //Index insert and remove
    public void insert(int val, int index) {
        CLNode cur = head;
        for (int i = 0; i < index; i++) {
            if (cur == null) {break;}
            cur = cur.getRight();
        }

        if (cur == null) {this.push(val);}
        else if (cur == head) {this.enqueue(val);}
        else {
            CLNode newBlock = new CLNode(val, cur.getLeft(), cur);
            cur.getLeft().setRight(newBlock);
            cur.setLeft(newBlock);
        }
    }

    public void remove(int val) {
        boolean found = false;
        CLNode cur = head;
        while (cur != null) {
            if (cur.getVal() == val) {
                found = true;
                break;
            }
            cur = cur.getRight();
        }
        if (found) {this.remove(cur);}
    }

    public void remove(CLNode node) {
        if (node.getLeft() == null) {this.dequeue();}
        else if (node.getRight() == null) {this.pop();}
        else {
            node.getLeft().setRight(node.getRight());
            node.getRight().setLeft(node.getLeft());
        }
    }

    public void removeAt(int index) {
        CLNode cur = head;
        for (int i = 1; i <= index; i++) {
            cur = cur.getRight();
        }
        if (cur.getRight() == null) {this.pop();}
        else if (cur.getLeft() == null) {this.dequeue();}
        else {
            cur.getLeft().setRight(cur.getRight());
            cur.getRight().setLeft(cur.getLeft());
        }
    }

    public void sortedInsert(int val) {
        int index = 0;
        boolean inserted = false;
        CLNode cur = head;
        while (cur != null) {
            if (cur.getVal() >= val) {
                this.insert(val, index);
                inserted = true;
                break;
            }
            cur = cur.getRight();
            index++;
        }
        if (!inserted) {this.push(val);}
    }

    public void removeDuplicates() {
        HashSet<Integer> tracker = new HashSet<Integer>();
        CLNode cur = head;
        while (cur != null) {
            int curVal = cur.getVal();
            CLNode nextNode = cur.getRight();
            if (tracker.contains(cur.getVal())) {
                this.remove(cur);
            }
            tracker.add(curVal);
            cur = nextNode;
        }
    }

    public void reverse() {
        CLNode oldHead = head;
        CLNode oldTail = tail;
        CLNode cur = head, next;
        while (cur != null) {
            next = cur.getRight();
            cur.setRight(cur.getLeft());
            cur.setLeft(next);
            cur = next;
        }
        head = oldTail;
        tail = oldHead;
    }

    @Override
    public ChainedList clone() {
        CLNode cur = head;
        ChainedList copy = new ChainedList();
        while(cur != null) {
            copy.push(cur.getVal());
            cur = cur.getRight();
        }
        return copy;
    }

    @Override
    public String toString() {
        String ans = "";
        CLNode cur = head;
        while(cur != null) {
            ans += ", " + cur.getVal();
            cur = cur.getRight();
        }
        if (ans.isEmpty()) {ans = ", ";}
        return "<" + ans.substring(2) + ">";
    }
}

class CLNode {
    private final int val;
    private CLNode left, right;
    public CLNode(int val, CLNode left, CLNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
    //GETTERS
    public int getVal() {return val;}
    public CLNode getLeft() {return left;}
    public CLNode getRight() {return right;}
    //SETTERS
    public void setLeft(CLNode left) {this.left = left;}
    public void setRight(CLNode right) {this.right = right;}
}
