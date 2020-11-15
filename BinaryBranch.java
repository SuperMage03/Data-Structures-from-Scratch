import java.util.ArrayList;

public class BinaryBranch {
    public static final int IN = 0, PRE = 1, POST = 2;
    private BranchNode root;

    public BinaryBranch() {root = null;}
    public void add(int val) {
        if (root == null) {root = new BranchNode(val);}
        else {root.add(val);}
    }

    public void add(BinaryBranch branch) {
        ArrayList<Integer> arr = branch.toArrayList();
        for (int a : arr) {this.add(a);}
    }

    public boolean contains(int val) {return root.depth(0, root, val) != -1;}

    public int depth(int val) {return root.depth(0, root, val);}

    public int height() {return root != null ? root.height(-1, root) : -1;}

    public int minHeight() {return root != null ? root.minHeight(-1, root) : -1;}

    public int countLeaves() {return root.countLeaves(root);}

    public ArrayList<Integer> toArrayList() {
        if (root == null) {return new ArrayList<Integer>();}
        else {return root.toArrayList(root);}
    }

    public void delete(int target) {delete(target, root, null);}

    public boolean isAncestor(int ancestor, int descendant) {
        BranchNode a = root.findNode(root, ancestor);
        if (a == null) {return false;}
        else {
            BinaryBranch newBB = new BinaryBranch();
            newBB.root = a;
            return newBB.contains(descendant);
        }
    }

    public boolean isBalanced() {
        boolean balanced1 = true, balanced2 = true;
        if (Math.abs(minHeight() - height()) > 1) {return false;}
        else {
            BinaryBranch newBB;
            if (root.getGreaterChild() != null) {
                newBB = new BinaryBranch();
                newBB.root = root.getGreaterChild();
                balanced1 = newBB.isBalanced();
            }
            if (root.getSmallerChild() != null) {
                newBB = new BinaryBranch();
                newBB.root = root.getSmallerChild();
                balanced2 = newBB.isBalanced();
            }
        }
        return balanced1 && balanced2;
    }

    public void delete(int target, BranchNode node, BranchNode before) {
        if (node != null) {
            if (node.getVal() == target) {
                ArrayList<Integer> holder = node.toArrayList(node);
                if (before != null) {
                    if (node.getVal() < before.getVal()) {before.setSmallerChild(null);}
                    else {before.setGreaterChild(null);}
                    for (int i = 1; i < holder.size(); i++) {
                        this.add(holder.get(i));
                    }
                }
                else {
                    if (holder.size() < 1) {root = null;}
                    else {root = new BranchNode(holder.get(1));}
                    for (int i = 2; i < holder.size(); i++) {
                        this.add(holder.get(i));
                    }
                }
            }
            else if (node.getVal() > target) {
                delete(target, node.getSmallerChild(), node);
            }
            else {
                delete(target, node.getGreaterChild(), node);
            }
        }
    }


    @Override
    public String toString() {
        String rtVal;
        rtVal = makeString(root, 0);
        if (!rtVal.isEmpty()) {rtVal = rtVal.substring(2);}
        return "<" + rtVal + ">";
    }

    public String toString(int mode) {
        String rtVal;
        if (mode == 0) {
            rtVal = toString();
        }
        else if (mode == 1) {
            rtVal = makeString(root, 1);
            if (!rtVal.isEmpty()) {rtVal = rtVal.substring(2);}
        }
        else {
            rtVal = makeString(root, 2);
            if (!rtVal.isEmpty()) {rtVal = rtVal.substring(2);}
        }
        return "<" + rtVal + ">";
    }

    public String makeString(BranchNode node, int mode) {
        if (node == null) {return "";}
        else {
            if (mode == 0) {
                return makeString(node.getSmallerChild(), 0) + ", " + node.getVal() + makeString(node.getGreaterChild(), 0);
            }
            else if (mode == 1) {
                return ", " + node.getVal() + makeString(node.getSmallerChild(), 1) + makeString(node.getGreaterChild(), 1);
            }
            else {
                return makeString(node.getSmallerChild(), 2) + makeString(node.getGreaterChild(), 2) + ", " + node.getVal();
            }
        }
    }
}

class BranchNode {
    private final int val;
    private BranchNode smallerChild, greaterChild;
    public BranchNode(int val) {
        this.val = val;
        smallerChild = null;
        greaterChild = null;
    }

    public void add(int val) {
        if (this.val < val) {
            if (greaterChild == null) {greaterChild = new BranchNode(val);}
            else {greaterChild.add(val);}
        }
        else if (this.val > val) {
            if (smallerChild == null) {smallerChild = new BranchNode(val);}
            else {smallerChild.add(val);}
        }
    }

    public int depth(int counter, BranchNode node, int val) {
        if (node == null) {return -1;}
        else {
            if (node.val == val) {return counter;}
            else if (node.val > val) {
                return depth(counter + 1, node.smallerChild, val);
            }
            else {
                return depth(counter + 1, node.greaterChild, val);
            }
        }
    }

    public BranchNode findNode(BranchNode node, int val) {
        if (node == null) {return null;}
        else {
            if (node.val == val) {return node;}
            else if (node.val > val) {
                return findNode(node.smallerChild, val);
            }
            else {
                return findNode(node.greaterChild, val);
            }
        }
    }

    public int height(int counter, BranchNode node) {
        if (node == null) {return counter;}
        else {
            return Math.max(height(counter + 1, node.smallerChild), height(counter + 1, node.greaterChild));
        }
    }

    public int minHeight(int counter, BranchNode node) {
        if (node.smallerChild == null && node.greaterChild == null) {return counter + 1;}
        else {
            if (node.smallerChild == null) {
                return minHeight(counter + 1, node.greaterChild);
            }
            else if (node.greaterChild == null) {
                return minHeight(counter + 1, node.smallerChild);
            }
            else {
                return Math.min(minHeight(counter + 1, node.smallerChild), minHeight(counter + 1, node.greaterChild));
            }
        }
    }

    public int countLeaves(BranchNode node) {
        int counter = 1;
        if (node.smallerChild == null && node.greaterChild == null) {return 1;}
        else {
            if (node.smallerChild != null) {counter += countLeaves(node.smallerChild);}
            if (node.greaterChild != null) {counter += countLeaves(node.greaterChild);}
        }
        return counter;
    }

    public ArrayList<Integer> toArrayList(BranchNode node) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        if (node == null) {return new ArrayList<Integer>();}
        else {
            arr.add(node.getVal());
            arr.addAll(toArrayList(node.getSmallerChild()));
            arr.addAll(toArrayList(node.getGreaterChild()));
            return arr;
        }
    }

    //Getters
    public int getVal() {return val;}
    public BranchNode getSmallerChild() {return smallerChild;}
    public BranchNode getGreaterChild() {return greaterChild;}
    //Setters
    public void setSmallerChild(BranchNode smallerChild) {this.smallerChild = smallerChild;}
    public void setGreaterChild(BranchNode greaterChild) {this.greaterChild = greaterChild;}
}
