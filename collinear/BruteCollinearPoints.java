import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkIllegal(points);
        int n = points.length;
        if (n < 4) {
            segments = new LineSegment[0];
            return;
        }
        Point[] pointsClone = Arrays.copyOf(points, n);
        Arrays.sort(pointsClone);

        ArrayList<LineSegment> segmentLst = new ArrayList<>();
        ArrayList<Double> slopeLst = new ArrayList<>();

        // Brute force to add line segment
        for (int p = 0; p < n - 3; p++) {
            Point p1 = pointsClone[p];
            for (int q = p + 1; q < n - 2; q++) {
                Point p2 = pointsClone[q];
                double slope12 = p1.slopeTo(p2);
                if (slopeLst.contains(slope12)) {
                    continue;
                }
                for (int r = q + 1; r < n - 1; r++) {
                    Point p3 = pointsClone[r];
                    double slope13 = p1.slopeTo(p3);
                    if (slope12 != slope13) {
                        continue;
                    }
                    int lastPoint = r + 1;
                    Point p4 = pointsClone[lastPoint];
                    double slope14 = p1.slopeTo(p4);
                    while (lastPoint < n) {
                        if (p1.slopeTo(pointsClone[lastPoint]) == slope12) {
                            p4 = pointsClone[lastPoint];
                            slope14 = p1.slopeTo(p4);
                        }
                        lastPoint++;
                    }
                    if (slope12 == slope14) {
                        segmentLst.add(new LineSegment(p1, p4));
                        slopeLst.add(slope12);
                        break;
                    }
                }
            }
        }

        segments = segmentLst.toArray(new LineSegment[segmentLst.size()]);

    }

    // Check the points are illegal or not
    private void checkIllegal(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points can't be null");
        }
        int n = points.length;
        if (points[n - 1] == null) {
            throw new IllegalArgumentException("Illegal points");
        }
        Point[] pointsClone = Arrays.copyOf(points, n);
        Arrays.sort(pointsClone);
        for (int i = 0; i < n - 1; i++) {
            if (pointsClone[i] == null || points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Illegal points");
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
       /* StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();*/

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            // segment.draw();
        }
        // StdDraw.show();
    }
}
