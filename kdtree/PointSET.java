import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;

public class PointSET {

    private final SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can't insert null point.");
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can't check null point.");
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangular can't be null");
        List<Point2D> pointInRect = new LinkedList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) pointInRect.add(p);
        }
        return pointInRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if (p == null) throw new IllegalArgumentException("Can't check null point.");
        if (points.isEmpty()) return null;
//        Point2D prevPoint = points.ceiling(p);
//        Point2D nextPoint = points.floor(p);
//        if (p.distanceTo(prevPoint) < p.distanceTo(nextPoint)) {
//            return prevPoint;
//        } else {
//            return nextPoint;
//        }
        Point2D nearestPt = points.min();
        for (Point2D pt : points) {
            if (pt.distanceTo(p) < nearestPt.distanceTo(p)) nearestPt = pt;
        }
        return nearestPt;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET pSet = new PointSET();
        System.out.println(pSet.isEmpty()); // true
        for (int i = 0; i < 10; i++) {
            pSet.insert(new Point2D((double) i/10, (double) i/10));
        }
        System.out.println(pSet.contains(new Point2D(0.2,0.2))); //true
        System.out.println(pSet.contains(new Point2D(0.1, 0.2))); // false
        System.out.println(pSet.isEmpty()); //false
        System.out.println(pSet.size()); // 10
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        pSet.draw();
        RectHV rect = new RectHV(0.2, 0.2, 0.6, 0.7);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.001);
        rect.draw();
        for (Point2D p : pSet.range(rect)) {
            System.out.print(p + "\t");
        }       // (0.2, 0.2)	(0.3, 0.3)	(0.4, 0.4)	(0.5, 0.5)	(0.6, 0.6)
        System.out.println();
        System.out.println(pSet.nearest(new Point2D(0.33, 0.33))); // (0.3, 0.3)
    }
}