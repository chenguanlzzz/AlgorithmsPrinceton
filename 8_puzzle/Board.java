

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private int n;
    private int[][] boardCur;
    private int[][] boardGoal;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles[0].length;
        if (n < 2 || n > 128){
            throw new IllegalArgumentException("n is not valid");
        }
        boardCur = copyTile(tiles);
        boardGoal = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boardGoal[i][j] = i * n + j + 1;
            }
        }
        boardGoal[n-1][n-1] = 0;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        for (int i = 0; i < n; i++) {
            sb.append('\n');
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", boardCur[i][j]));
            }
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int error = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardGoal[i][j] == 0) continue;
                if (boardGoal[i][j] != boardCur[i][j]) error++;
            }
        }
        return error;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int error = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardGoal[i][j] == 0) continue;
                if (boardGoal[i][j] != boardCur[i][j]) {
                    error += errorAdd(i, j);
                }
            }
        }
        return error;
    }

    // return distance of the element between goal and tiles
    private int errorAdd(int i, int j) {
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (boardCur[x][y] == boardGoal[i][j]) {
                    return Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        return 0;
    }

    // return the tile at x, y
    private int tileAt(int x, int y) {
        return boardCur[x][y];
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (y.getClass() != this.getClass()) return false;

        Board boardY = (Board) y;
        if (boardY.dimension() != this.dimension()) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardY.tileAt(i, j) != boardCur[i][j]) return false;
            }
        }
        return true;
    }

    // find current blank place
    private int[] findBlank() {
        int[] blank = new int[2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardCur[i][j] == 0) {
                    blank[0] = i;
                    blank[1] = j;
                    break;
                }
            }
        }
        return blank;
    }

    // Return the copy of current tiles
    private int[][] copyTile(int[][] tiles) {
        int[][] copy = new int[tiles[0].length][tiles[0].length];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    // Swap the tile at this board
    private static void swap(int[][]tiles, int rowA, int colA, int rowB, int colB) {
        int tmp = tiles[rowA][colA];
        tiles[rowA][colA] = tiles[rowB][colB];
        tiles[rowB][colB] = tmp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbs = new LinkedList<>();
        int[] blankIndex = findBlank();
        int blankRow = blankIndex[0];
        int blankCol = blankIndex[1];

        if (blankRow > 0) {
            int[][] north = copyTile(boardCur);
            swap(north, blankRow, blankCol, blankRow-1, blankCol);
            neighbs.add(new Board(north));
        }
        if (blankRow < n-1) {
            int[][] south = copyTile(boardCur);
            swap(south, blankRow, blankCol, blankRow+1, blankCol);
            neighbs.add(new Board(south));
        }
        if (blankCol > 0) {
            int[][] west = copyTile(boardCur);
            swap(west, blankRow, blankCol, blankRow, blankCol-1);
            neighbs.add(new Board(west));
        }
        if (blankCol < n - 1) {
            int[][] east = copyTile(boardCur);
            swap(east, blankRow, blankCol, blankRow, blankCol+1);
            neighbs.add(new Board(east));
        }
        return neighbs;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinBoard = copyTile(boardCur);
        int[] blank = findBlank();
        if (blank[0] != 0) {
            swap(twinBoard, 0, 0, 0, 1);
        } else {
            swap(twinBoard, 1, 0, 1, 1);
        }
        return new Board(twinBoard);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int [][] arr = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board b = new Board(arr);
        StdOut.println(b.toString());
        Iterable<Board> neighbours = b.neighbors();
        for (Board n : neighbours) {
            StdOut.println(n.toString());
        }
        StdOut.print("Hamming: ");
        StdOut.println(b.hamming());
        StdOut.print("Manhattan: ");
        StdOut.println(b.manhattan());
        StdOut.println(b.twin().toString());
    }

}