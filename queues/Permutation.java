import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args){
        RandomizedQueue<String> RandomQue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            RandomQue.enqueue(StdIn.readString());
        }
        int i = 0;
        int j = Integer.parseInt(args[0]);
        for (String x : RandomQue) {

            if (i == j) {
                break;
            }
            System.out.println(x);
            i++;
        }
    }
}