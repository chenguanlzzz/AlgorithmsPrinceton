import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private Node node;
    private MinPQ<Node> nodeQue;
    private LinkedList<Board> path;
    private int totalMoves;
    private boolean solvable;


    private class Node {
        public Board current;
        public int moves;
        public Node previous;
        public int estDis;


        public Node (Board b, int m, Node n) {
            current = b;
            moves = m;
            previous = n;
            estDis = b.manhattan();
        }
    }

    private class disComparator implements Comparator<Node> {
        public int compare(Node a, Node b) {
            int disA = a.moves + a.estDis;
            int disB = b.moves + b.estDis;
            return disA - disB;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("this is null!");
        }
        node = new Node(initial, 0, null);
        nodeQue = new MinPQ<>(new disComparator());
        nodeQue.insert(node);
        solvable = false;

        Node nodeSwap = new Node(initial.twin(), 0, null);
        MinPQ<Node> nodeQueSwap = new MinPQ<>(new disComparator());
        nodeQueSwap.insert(nodeSwap);

        while (!nodeQue.isEmpty() && !nodeQueSwap.isEmpty()) {
            Node nextNode = nodeQue.delMin();
            node = nextNode;
            if (node.estDis == 0) {
                solvable = true;
                break;
            }

            Node nextNodeSwap = nodeQueSwap.delMin();
            nodeSwap = nextNodeSwap;
            if (nodeSwap.estDis == 0) {
                break;
            }

            for (Board board : node.current.neighbors()) {
                if (node.previous == null || !board.equals(node.previous.current)) {
                    nodeQue.insert(new Node(board, node.moves+1, node));
                }
            }

            for (Board boardSwap : nodeSwap.current.neighbors()) {
                if (nodeSwap.previous == null || !boardSwap.equals(nodeSwap.previous.current)) {
                    nodeQueSwap.insert(new Node(boardSwap, nodeSwap.moves+1, nodeSwap));
                }
            }
        }
        if (solvable) {
            Node tmp = node;
            totalMoves = node.moves;
            path = new LinkedList<>();
            while (tmp != null) {
                path.addFirst(tmp.current);
                tmp = tmp.previous;
            }
        } else {
            totalMoves = -1;
            path = null;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return totalMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return path;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}