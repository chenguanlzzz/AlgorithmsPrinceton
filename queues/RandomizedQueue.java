import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;
    private int pos;
    private boolean[] removed;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        size = 0;
        pos = 0;
        removed = new boolean[2];
        for (int i = 0; i < 2; i++) {
            removed[i] = true;
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // Resize if too big or too small
    private void resize(int n){
        Item[] newItems = (Item[]) new Object[n];
        int index = 0;
        for (int i = 0; i < items.length; i++) {
            if (removed[i]) {
                continue;
            }
            newItems[index] = items[i];
            index++;
        }
        items = newItems;
        removed = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (i < size) {
                removed[i] = false;
            } else {
                removed[i] = true;
            }
        }
        pos = size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add null item.");
        }
        if (size == items.length){
            resize(2 * size);
        }
        if (pos >= items.length) {
            resize(size*2);
        }

        items[pos] = item;
        removed[pos] = false;
        pos++;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Items is empty.");
        }
        if (size > 10 && size == 0.5 * items.length) {
            resize(size);
        }
        int i = StdRandom.uniform(pos);
        while (removed[i]) {
            i = StdRandom.uniform(pos);
        }
        removed[i] = true;
        size--;
        return items[i];
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Items is empty.");
        }
        int i = StdRandom.uniform(pos);
        while (removed[i]) {
            i = StdRandom.uniform(pos);
        }
        return items[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        resize(items.length);
        return new RandomQueIterator();
    }

    private class RandomQueIterator implements Iterator<Item> {
        private Item[] QueIter;
        private int index;
        private boolean[] addOrNot;

        public RandomQueIterator() {
            QueIter = (Item[]) new Object[size];
            index = 0;
            addOrNot = new boolean[size];
            for (int i = 0; i < size; i++) {
                int j = StdRandom.uniform(size);
                while (addOrNot[j]) {
                    j++;
                    if (j == size) {
                        j = 0;
                    }
                }
                addOrNot[j] = true;
                QueIter[j] = items[i];
            }
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item T = QueIter[index];
            index++;
            return T;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> x = new RandomizedQueue<>();
        System.out.println(x.isEmpty());
        x.enqueue(1);
        System.out.println(x.isEmpty());
        x.enqueue(8);
        int f = x.sample();
        System.out.println(f);
        f = x.dequeue();
        System.out.println(f);
        System.out.println(x.size());
        x.enqueue(7);
        x.enqueue(1);
        x.enqueue(4);
        x.enqueue(3);
        x.enqueue(15);
        x.enqueue(100);
        x.enqueue(20);
        x.enqueue(1000);
        x.enqueue(5603);
        x.iterator();
        for (int i : x) {
            System.out.println((i));
        }
    }

}