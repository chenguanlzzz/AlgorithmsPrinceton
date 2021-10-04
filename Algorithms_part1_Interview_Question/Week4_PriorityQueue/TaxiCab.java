import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class TaxiCab implements Comparable<TaxiCab> {

    private final long sum;
    private final int i;
    private final int j;

    public TaxiCab(int i, int j) {
        this.i = i;
        this.j = j;
        this.sum = (long) i * i * i + (long) j * j * j;
    }

    @Override
    public int compareTo(TaxiCab that) {
        if (this.sum < that.sum) return -1;
        else if (this.sum > that.sum) return +1;
        else if (this.i < that.i) return -1;
        else if (this.i > that.i) return +1;
        else return 0;
    }

    public String toString() {
        return i + "^3 + " + j + "^3";
    }

    public static void main(String[] args) {
        // find a^3 + b^3 = c^3 + d^3, where a, b, c, d <= n
        int n = 100;

        // Initialize priority queue
        MinPQ<TaxiCab> pq = new MinPQ<>();
        for (int i = 1; i <= n; i++) {
            pq.insert(new TaxiCab(i, i));
        }

        // enumerate sums in ascending order, look for repeated sums
        int run = 1;
        TaxiCab prev = new TaxiCab(0, 0);   // sentinel

        while (!pq.isEmpty()) {
            TaxiCab curr = pq.delMin();
            if (curr.sum == prev.sum) {
                run++;
                if (run == 2) StdOut.print(prev.sum + " = " + prev);
                StdOut.print(" = " + curr);
            }
            else {
                if (run > 1) StdOut.println();
                run = 1;
            }
            prev = curr;
            if (curr.j < n) pq.insert(new TaxiCab(curr.i, curr.j + 1));

        }
        if (run > 1) StdOut.println();
    }
}
