import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int amount = kb.nextInt();
        int[] arr = new int[amount];
        for (int i = 0; i < amount; i++) {
            arr[i] = kb.nextInt();
        }
        SegmentTreeV2 ST = new SegmentTreeV2(arr, SegmentTree.MAX);
        ST.update(0, 99);
        while (true) {
            int QL = kb.nextInt();
            int QR = kb.nextInt();
            System.out.println(ST.query(QL, QR));
        }
    }
}
