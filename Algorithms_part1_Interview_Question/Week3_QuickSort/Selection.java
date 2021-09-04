import java.util.Arrays;

public class Selection {

    public static int select(int[] a, int[] b, int k) {
        return select(a, 0, a.length - 1, b, 0, b.length - 1, k);
    }

    private static int select (int[] a, int aLo, int aHi, int[] b, int bLo, int bHi, int k) {

//        if (k == aLo + bLo + 1) return a[aLo] < b[bLo] ? a[aLo] : b[bLo];
        if (aLo > aHi) return b[bLo+k-1];
        if (bLo > bHi) return a[aLo+k-1];
        int bt = bLo + (k-1) / 2 > bHi ? bHi : bLo + (k-1) / 2 ;
        int at = aLo + (k-1) / 2 > aHi ? aHi : aLo + (k-1) / 2 ;
        if (k == 1) return a[aLo] < b[bLo] ? a[aLo] : b[bLo];

        if (k == 2) {
            if (bLo < bHi && a[aLo] > b[bLo+1]) return b[bLo+1];
            else if (aLo < aHi && b[bLo] > a[aLo+1]) return a[aLo+1];
            else return a[aLo] > b[bLo] ? a[aLo] : b[bLo];
        }
        if (aLo == aHi && a[aLo] < b[bt]) return select(a, aLo+1, aHi, b, bt, bHi, k-(bt-bLo)-1);
        if (bLo == bHi && b[bLo] < a[at]) return select(a, at, aHi, b, bLo+1, bHi, k-(at-aLo)-1);

        if (a[at] < b[bt]) {
            return select(a, at , aHi, b, bLo, bt, k-(at-aLo));
        } else {
            return select(a, aLo, at, b, bt , bHi, k-(bt-bLo));
        }

    }

    public static void main(String[] args) {
        int n = 10;
        int n1 = StdRandom.uniform(n);
        int n2 = n-n1;
        int[] a = new int[n1];
        int[] b = new int[n2];
        for(int i=0;i<n1;i++){
            a[i] = StdRandom.uniform(20);
        }
        for(int i=0;i<n2;i++) {
            b[i] = StdRandom.uniform(20);
        }
        Arrays.sort(a);
        Arrays.sort(b);
//        int[] a = {0, 8, 11, 16};
//        int[] b = {3, 4, 12, 18, 18, 19};
        System.out.println("a="+Arrays.toString(a));
        System.out.println("b="+Arrays.toString(b));
        int k =StdRandom.uniform(1,n);

//        int k = 6;
        int largestK = select(a, b, k);
        System.out.println("The "+k+"th element is "+largestK);
    }

}
