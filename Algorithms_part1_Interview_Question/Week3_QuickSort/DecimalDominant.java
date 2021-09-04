import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecimalDominant {
    public static List<Integer> decimalDominant(int[] a) {
        List<Integer> dominatLst = new ArrayList<>();
        decimalDominant(a, 0, a.length - 1, dominatLst);
        return dominatLst;
    }

    private static void exch(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void  decimalDominant(int[] a, int lo, int hi, List<Integer> lst) {
        if (hi - lo < 3) return; // dominant for 3 to test
        int lt = lo, gt = hi;
        int pivot = a[lo];
        int i = lo;
        while (i <= gt) {
            int cmp = a[i] - pivot;
            if (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, gt--, i);
            else i++;
        }
        if (gt - lt >= 3) lst.add(a[lt]);  // dominant for 3
        decimalDominant(a, lo, lt-1, lst);
        decimalDominant(a, gt+1, hi, lst);
    }



    public static void main(String[] args) {
        int[] ar = new int[]{1, 1, 3, 3, 3, 3, 2, 1, 1, 1, 2, 4, 3, 2, 11, 23, 2, 2, 4, 1123, 5, 5, 5, 4, 5};
        List<Integer> lst = decimalDominant(ar);
        System.out.println(Arrays.toString(lst.toArray()));
    }
}
