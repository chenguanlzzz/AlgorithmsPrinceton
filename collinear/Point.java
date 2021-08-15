import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        int comp = this.y - that.y;
        if (comp == 0) {
            return this.x - that.x;
        }
        return comp;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if(that.y > this.y) {
                return Double.POSITIVE_INFINITY;
            } else {
                return Double.NEGATIVE_INFINITY;
            }
        }
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new slopeComparator();
    }

    private class slopeComparator implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            if (slope1 < slope2) {
                return -1;
            } if (slope1 > slope2) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}