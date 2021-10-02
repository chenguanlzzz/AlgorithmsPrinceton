import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;


public class MedianPQ<T extends Comparable<T>> {
    private MinPQ<T> larger;
    private MaxPQ<T> lesser;
    private int n;

    public MedianPQ() {
        larger = new MinPQ<>();
        lesser = new MaxPQ<>();
        n = 0;
    }

    public void insert(T a) {
        if (n == 0) larger.insert(a);
        else if (a.compareTo(larger.min()) > 0) larger.insert(a);
        else lesser.insert(a);
        n++;

        // if one of the PQ is two more larger than the other. remove the element of bigger one to smaller one.
        if (larger.size() > lesser.size() + 1) {
            lesser.insert(larger.delMin());
        }
        else if (lesser.size() > larger.size() + 1) {
            larger.insert((lesser.delMax()));
        }
    }

    public int size() {
        return n;
    }

    public T med() {
        if (larger.size() > lesser.size()) return larger.min();
        else return lesser.max();
    }

    public T delMed() {
        n--;
        if (larger.size() > lesser.size()) return larger.delMin();
        else return lesser.delMax();
    }


    public static void main(String[] args) {
        MedianPQ<Integer> medPQ = new MedianPQ<>();
        for (int i = 1; i <= 15; i++) {
            medPQ.insert(i);
        }
        System.out.println("Median is " + medPQ.delMed().toString());
        System.out.println("Median is " + medPQ.delMed().toString());
        System.out.println("Median is " + medPQ.delMed().toString());
    }
}
