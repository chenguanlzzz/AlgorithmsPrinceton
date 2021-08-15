import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkIllegal(points);
        int n = points.length;
        Point[] pointsClone = new Point[n - 1];
        ArrayList<LineSegment> segmentLst = new ArrayList<>();
        ArrayList<Double> slopeLst = new ArrayList<>();

        if (n < 4) {
            segments = new LineSegment[0];
            return;
        }

        for (int k = 0; k < n; k++) {
            pointsClone = arrClone(points, k);
            Point org = points[k];
            Arrays.sort(pointsClone, org.slopeOrder());
            int i = 0;
            int j = 2;
            while (j < n - 1) {

                double slope1 = org.slopeTo(pointsClone[i]);
                double slope2 = org.slopeTo(pointsClone[j]);

                if (slopeLst.contains(slope1)) {
                    i++;
                    j++;
                    continue;
                }

                // if it is same line
                if (slope1 == slope2) {
                    while (j < n - 2 && slope1 == org.slopeTo(pointsClone[j+1])) {
                        j++;
                    }
                    Point[] slopeSameArr = slopeSameArr(pointsClone, i, j, org);
                    if (!slopeLst.contains(slopeSameArr[0].slopeTo(slopeSameArr[1]))) {
                        segmentLst.add(new LineSegment(slopeSameArr[0], slopeSameArr[slopeSameArr.length - 1]));
                        slopeLst.add(slopeSameArr[0].slopeTo(slopeSameArr[1]));
                    }
                    i = j + 1;
                    j = i + 2;
                } else {
                    i++;
                    j++;
                }
            }
        }

        segments = segmentLst.toArray(new LineSegment[segmentLst.size()]);
    }

    // Clone all the points to new array
    private Point[] arrClone(Point[] points, int org) {
        Point[] pointsClone = new Point[points.length - 1];
        int j = 0;
        for (int i = 0; i < points.length; i++) {
            if (i == org) {
                continue;
            }
            pointsClone[j++] = points[i];
        }
        return pointsClone;
    }

    private Point[] slopeSameArr(Point[] points, int i, int j, Point org) {
        Point[] slopeSameArr = new Point[j - i + 2];
        System.arraycopy(points, i, slopeSameArr, 0, j - i + 1);
        slopeSameArr[j - i + 1] = org;
        Arrays.sort(slopeSameArr);
        return slopeSameArr;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    // Cheack the points are illegal or not
    private void checkIllegal(Point[] points) {
        int n = points.length;
        if (points == null || points[n - 1] == null) {
            throw new IllegalArgumentException("Illegal points");
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (points[i] == null || points[i] == points[j]) {
                    throw new IllegalArgumentException("Illegal points");
                }
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
       /* StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();*/

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            //segment.draw();
        }
        //StdDraw.show();
    }
}