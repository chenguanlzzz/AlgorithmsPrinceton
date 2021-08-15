import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
        Arrays.sort(points);

        ArrayList<LineSegment> segmentLst = new ArrayList<>();
        ArrayList<Double> slopeLst = new ArrayList<>();

        // Brute force to add line segment
        for (int p = 0; p < n - 3; p++) {
            boolean brk = true;
            for (int q = p + 1; q < n - 2 && brk; q++) {
                double slope1 = points[p].slopeTo(points[q]);
                if (slopeLst.contains(slope1)) {
                    continue;
                }
                for (int r = q + 1; r < n - 1 && brk; r++) {
                    double slope2 = points[p].slopeTo(points[r]);
                    if (slope1 != slope2) {
                        continue;
                    }
                    for (int s = r + 1; s < n && brk; s++) {
                        double slope3 = points[p].slopeTo(points[s]);
                        if (slope2 != slope3) {
                            continue;
                        }
                        int newS = s + 1;
                        while(newS < n) {
                            if (slope2 == points[p].slopeTo(points[newS])) {
                                s = newS;
                            }
                            newS++;
                        }
                        segmentLst.add(new LineSegment(points[p], points[s]));
                        slopeLst.add(slope1);
                        brk = false;
                    }
                }
            }
        }

        segments = segmentLst.toArray(new LineSegment[segmentLst.size()]);

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

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
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
            //segment.draw();
        }
        //StdDraw.show();
    }
}