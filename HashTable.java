import java.util.LinkedList;

public class HashTable {
    private static final int p = 31;
    private static final int m = 1000000009;
    private static final int[] powArr = new int[2000];
    private final LinkedList[] HT;
    private static int calcHash(String val){
        int index = val.length();
        int cur = 0;
        for (int i = 0; i < index; i++){
            cur += ((int) val.charAt(index-(i+1)) * powArr[i]) % m;
            if (powArr[i+1] == 0) {
                powArr[i+1] = (powArr[i] * p) % m;
            }
        }
        return cur;
    }
    public HashTable() {HT = new LinkedList[2000];}

    public void add(String str) {
        int hashVal = calcHash(str) % HT.length;
        if (HT[hashVal] == null) {
            HT[hashVal] = new LinkedList();
            HT[hashVal].add(str);
        }
    }

    public boolean contains(String str) {
        int hashVal = calcHash(str) % HT.length;
        return HT[hashVal] != null && HT[hashVal].contains(str);
    }

    @Override
    public String toString() {
        String rtVal = "";
        for (LinkedList ll : HT) {
            if (ll != null) {
                for (Object o : ll) {
                    rtVal += ", " + o;
                }
            }
        }
        return rtVal.isEmpty() ? "<>" : "<" + rtVal.substring(2) + ">";
    }
}
