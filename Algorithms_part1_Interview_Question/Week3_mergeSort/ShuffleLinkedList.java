import java.util.Iterator;

public class ShuffleLinkedList<T extends Comparable<T>> implements Iterable<T> {

    private Node head;
    private int size;

    /* Linked List */
    private class Node {
        private T data;
        private Node next;

        public Node() {
        }

        public Node(T i, Node n) {
            data = i;
            next = n;
        }
    }

    public ShuffleLinkedList() {
        head = null;
        size = 0;
    }

    public ShuffleLinkedList(T data) {
        head = new Node(data, null);
        size = 1;
    }

    public void add(T data) {
        if (head == null) {
            head = new Node(data, null);
            return;
        }
        Node node = head;
        while (node.next != null) {
            node = node.next;
        }
        node.next = new Node(data, null);
        size++;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T data = current.data;
            current = current.next;
            return data;
        }
    }

    @Override
    public String toString() {
        Iterator<T> iter = iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            sb.append(iter.next());
            sb.append("->");
        }
        int len = sb.length();
        sb.delete(len - 2, len);
        return sb.toString();
    }

    public void shuffle() {
        head = shuffle(head);
    }

    private Node shuffle(Node node) {
        if (node == null || node.next == null) return node;
        Node left = node;
        Node right = node.next;
        Node leftRun = left;
        Node rightRun = right;

        while (rightRun.next != null && rightRun.next.next != null) {
            leftRun.next = leftRun.next.next;
            rightRun.next = rightRun.next.next;
            leftRun = leftRun.next;
            rightRun = rightRun.next;
        }
        leftRun.next = null;  // Break left and right linked list
        left = shuffle(left);
        right = shuffle(right);
        return merge(left, right);
    }

    private Node merge(Node left, Node right) {
        Node aux = new Node();
        Node l = left;
        Node r = right;
        Node current = aux;
        while (l != null || r != null) {
            if (l == null) {
                current.next = new Node(r.data, null);
                current = current.next;
                r = r.next;
                continue;
            }
            else if (r == null) {
                current.next = new Node(l.data, null);
                current = current.next;
                l = l.next;
                continue;
            }

            double flip = StdRandom.uniform();
            if (flip < 0.5) {
                current.next = new Node(l.data, null);
                current = current.next;
                l = l.next;
            }
            else {
                current.next = new Node(r.data, null);
                current = current.next;
                r = r.next;
            }
        }
        return aux.next;
    }

    public static void main(String[] args) {
        ShuffleLinkedList<Integer> sll = new ShuffleLinkedList<Integer>();
        sll.add(1);
        sll.add(2);
        sll.add(11);
        sll.add(9);
        sll.add(10);
        sll.add(4);
        sll.add(7);
        System.out.println(sll);
        sll.shuffle();
        System.out.println(sll);
    }
}
