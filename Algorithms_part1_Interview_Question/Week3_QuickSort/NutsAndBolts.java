public class NutsAndBolts {
    public static void match (Comparable[] a, Comparable[] b){
        StdRandom.shuffle(a);
        StdRandom.shuffle(b);
        quickSort(a, b, 0, a.length-1);
    }
    private static void quickSort(Comparable[] a, Comparable[]b, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int i = partition(a, b[lo], lo, hi);
        partition(b, a[i], lo, hi);
        quickSort(a, b, lo, i-1);
        quickSort(a, b, i+1, hi);
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] a, int i , int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static int partition(Comparable[] a, Comparable b, int lo, int hi){
        Comparable[] aux = new Comparable[hi - lo + 1];
        int k = 0;
        int ans = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = lo; j <= hi; j++) {
                if (i == 0 && less(a[j], b)) {
                    aux[k++] = a[j];
                } else if (i == 1 && a[j].compareTo(b) == 0) {
                    aux[k++] = a[j];
                    ans = j;
                } else if (i == 2 && less(b, a[j])) {
                    aux[k++] = a[j];
                }
            }
        }
        System.arraycopy(aux, 0, a, lo, hi - lo + 1);
        return ans;
    }

    private static String toString(Comparable[] a){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]);
            sb.append(' ');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Integer[] nuts = {3, 1, 2, 7, 8};
        Integer[] bolts = {2, 8, 3, 7, 1};
        match(nuts, bolts);
        System.out.println(toString(nuts));
        System.out.println(toString(bolts));
    }
}
