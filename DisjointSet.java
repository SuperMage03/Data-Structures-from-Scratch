import java.util.HashMap;

public class DisjointSet {
    private static class Node {
        public int parent = Integer.MIN_VALUE, subsetRank = 0;
    }

    private final HashMap<Integer, Node> graph = new HashMap<Integer, Node>();

    private int findParent(int n) {
        if (!graph.containsKey(n)) {graph.put(n, new Node());}

        Node node = graph.get(n);
        if (node.parent == Integer.MIN_VALUE || node.parent == n) {
            node.parent = n;
            return n;
        }
        node.parent = findParent(node.parent);
        return node.parent;
    }


    public void connect(int p1, int p2) {
        int px = findParent(p1), py = findParent(p2);
        if (px != py) {
            Node node1 = graph.get(px), node2 = graph.get(py);
            if (node1.subsetRank > node2.subsetRank) {node2.parent = p1;}
            else if (node1.subsetRank < node2.subsetRank) {node1.parent = p2;}
            else {
                node2.parent = p1;
                node1.subsetRank++;
            }
        }
    }

    public boolean connected(int p1, int p2) {
        return findParent(p1) == findParent(p2);
    }
}
