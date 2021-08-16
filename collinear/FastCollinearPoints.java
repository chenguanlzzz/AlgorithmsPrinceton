import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkIllegal(points);
        int n = points.length;
        ArrayList<LineSegment> segmentLst = new ArrayList<>();
        // ArrayList<Double> slopeLst = new ArrayList<>();

        if (n < 4) {
            segments = new LineSegment[0];
            return;
        }

        Point[] pointsClone = Arrays.copyOf(points, n);

        for (Point p : points) {
            Arrays.sort(pointsClone, p.slopeOrder());
            int count = 1;
            int i = 1;
            double slope = p.slopeTo(pointsClone[0]);
            for (; i < n; i++) {
                double slope2 = p.slopeTo(pointsClone[i]);
                if (slope2 == slope) {
                    count++;
                    continue;
                } else if (count >= 3) {
                    Arrays.sort(pointsClone, i - count, i);
                    if (p.compareTo(pointsClone[i - count]) < 0) {
                        segmentLst.add(new LineSegment(p, pointsClone[i - 1]));
                    }
                }
                count = 1;
                slope = slope2;
            }
            if (count >= 3) {
                Arrays.sort(pointsClone, i - count, i);
                if (p.compareTo(pointsClone[i - count]) < 0) {
                    segmentLst.add(new LineSegment(p, pointsClone[i - 1]));
                }
            }
        }
        segments = segmentLst.toArray(new LineSegment[segmentLst.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
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
            if (pointsClone[i] == null || pointsClone[i].compareTo(pointsClone[i + 1]) == 0) {
                throw new IllegalArgumentException("Illegal points");
            }
        }
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
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
