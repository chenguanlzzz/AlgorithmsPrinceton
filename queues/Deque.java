import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class LinkedNode{
        public Item item;
        public LinkedNode prev;
        public LinkedNode next;

        public LinkedNode(Item i, LinkedNode p, LinkedNode n){
            item = i;
            prev = p;
            next = n;
        }
    }

    private LinkedNode sentinel;
    private int size;


    // construct an empty deque
    public Deque() {
        sentinel = new LinkedNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }



    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add null item.");
        }
        sentinel.next = new LinkedNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add null item.");
        }
        sentinel.prev = new LinkedNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Items is empty.");
        }
        Item T = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return T;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Items is empty.");
        }
        Item T = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return T;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Iterator for ArrayQue
    private class DequeIterator implements Iterator<Item> {
        private LinkedNode nodeIter = sentinel;
        private int x = 0;

        @Override
        public boolean hasNext() {
            return x < size;
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
            nodeIter = nodeIter.next;
            Item T = nodeIter.item;
            x++;
            return T;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> x = new Deque<>();
        System.out.println(x.isEmpty());
        x.addFirst(1);
        System.out.println(x.isEmpty());
        x.addFirst(8);
        System.out.println(x.removeLast());
        System.out.println(x.size());
        x.addLast(7);
        System.out.println(x.removeFirst());
        x.addFirst(1);
        x.addLast(4);
        x.addLast(3);
        x.addLast(15);
        x.addFirst(20);
        x.addFirst(14);
        x.addLast(100);
        for (int i : x) {
            System.out.println((i));
        }
    }

}
