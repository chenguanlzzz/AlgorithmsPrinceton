import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] percoMatrix;
    private final int n;
    private final WeightedQuickUnionUF percoUF;
    private int openSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must >= 0");
        }

        percoMatrix = new boolean[n][n];
        this.n = n;
        percoUF = new WeightedQuickUnionUF(n * n + 2);  // Two more node for top & bottom secret node.
        openSize = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                percoMatrix[i][j] = false;
            }
        }
    }

    // change the xy correspond to 1D number
    private int xyTo1D(int x, int y) {
        return (x - 1) * n + y - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int cur = xyTo1D(row, col);
        percoMatrix[row - 1][col - 1] = true;
        openSize += 1;

        // Check the surrounding is fall or not, if it is, make this element fall
        if (row > 1 && isOpen(row - 1, col)) {
            percoUF.union(cur, cur - n);
        }
        if (row < n && isOpen(row + 1, col)) {
            percoUF.union(cur, cur + n);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            percoUF.union(cur, cur - 1);
        }
        if (col < n && isOpen(row, col + 1)) {
            percoUF.union(cur, cur + 1);
        }
        if (row == n) {
            if (isFull(row, col)) {
                percoUF.union(n * n + 1, cur);
            } else if (!percolates()) {
                percoUF.union(n * n + 1, cur);
            }
        }
        if (row == 1) {
            percoUF.union(n * n, cur);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > n || col > n || row <= 0 || col <= 0) {
            throw new IllegalArgumentException("Out of index.");
        }
        return percoMatrix[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            return percoUF.find(xyTo1D(row, col)) == percoUF.find(n * n);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        return percoUF.find(n * n) == percoUF.find(n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation x = new Percolation(3);
        x.open(1, 1);
        System.out.println(x.isOpen(1, 1));
        System.out.println(x.isFull(1, 1));
        System.out.println(x.isFull(2, 1));
        System.out.println(x.isOpen(2, 1));
        x.open(2, 1);
        System.out.println(x.percolates());
        //System.out.println(x.isOpen(1, 0));
        //System.out.println(x.isFull(1, 0));

        x.open(3, 1);
        System.out.println(x.percolates());
        x.open(3, 1);
        x.open(3, 3);
        System.out.println(x.isFull(3, 3));
        System.out.println(x.numberOfOpenSites());
    }
}
