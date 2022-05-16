import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;
    private final RectHV rect;

    private class Node {
        private final Point2D p;
        private final int level;
        private RectHV rectSmaller;
        private RectHV rectLarger;
        private Node smaller;
        private Node larger;

        public Node(Point2D p, int level, RectHV rect) {
            this.p = p;
            this.level = level;
            if (this.level % 2 == 0) { // Split by X
                this.rectSmaller = new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
                this.rectLarger = new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            } else {    // Split by Y
                this.rectSmaller = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
                this.rectLarger = new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
            }

        }

        public Point2D getPoint() {
            return p;
        }

        public RectHV getSmallerRect() {
            return rectSmaller;
        }

        public RectHV getLargerRect() {
            return rectLarger;
        }

    }
    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
        rect = new RectHV(0, 0, 1, 1);
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can't insert null point.");
        root = insert(root, p,0, rect);
    }

    private Node insert(Node root, Point2D p, int level, RectHV rect) {
        if (root == null) {
            size++;
            return new Node(p, level, rect);
        }
        if (root.getPoint().equals(p)) return root;
        if (root.getSmallerRect().contains(p)) root.smaller = insert(root.smaller, p, level + 1, root.getSmallerRect());
        else root.larger = insert(root.larger, p, level + 1, root.getLargerRect());
        return root;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can't check null point.");
        return contains(root, p);
    }

    private boolean contains(Node root, Point2D p) {
        if (root == null) return false;
        if (root.getPoint().equals(p)) return true;
        if (root.getSmallerRect().contains(p)) return contains(root.smaller, p);
        else return contains(root.larger, p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.clear();
        draw(root);
    }

    private void draw(Node root) {
        if (root == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        root.getPoint().draw();
        if (root.level % 2 == 0) { // Split by X
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(root.getPoint().x(), root.getSmallerRect().ymin(), root.getPoint().x(), root.getSmallerRect().ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(root.getSmallerRect().xmin(), root.getPoint().y(), root.getSmallerRect().xmax(), root.getPoint().y());
        }
        draw(root.smaller);
        draw(root.larger);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangular can't be null");
        List<Point2D> pointInRect = new LinkedList<>();
        range(root, rect, pointInRect);
        return pointInRect;
    }

    private void range(Node root, RectHV rect, List<Point2D> lst) {
        if (root == null) return;
        if (rect.contains(root.p)) lst.add(root.p);
        if (rect.intersects(root.getSmallerRect())) range(root.smaller, rect, lst);
        if (rect.intersects(root.getLargerRect())) range(root.larger, rect, lst);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if (p == null) throw new IllegalArgumentException("Can't check null point.");
        return nearest(root, p, root.p);
    }

    private Point2D nearest(Node root, Point2D p, Point2D nearestPt) {
        if (root == null) return nearestPt;
        if (root.p.distanceTo(p) < nearestPt.distanceTo(p)) nearestPt = root.getPoint(); // if the of this node has shorter distance to target, update the nearest point.

        if (root.rectSmaller.contains(p)) {  // if the target point is in the one side rectangle. Search first in the rectangle that contains the point.
            nearestPt = nearest(root.smaller, p, nearestPt);
            if (nearestPt.distanceTo(p) > root.rectLarger.distanceTo(p)) { // then if the the other rectangle has shorter than current nearest, go search the other rectangle. else don't need to search.
                nearestPt = nearest(root.larger, p, nearestPt);
            }
        } else {
            nearestPt = nearest(root.larger, p, nearestPt);
            if (nearestPt.distanceTo(p) > root.rectSmaller.distanceTo(p)) {
                nearestPt = nearest(root.smaller, p, nearestPt);
            }
        }
        return nearestPt;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree pTree = new KdTree();
//        System.out.println(pTree.isEmpty()); // true
//        for (int i = 0; i < 10; i++) {
//            pTree.insert(new  Point2D((double) i/10, (double) i/10));
//        }
//        System.out.println(pTree.contains(new Point2D(0.2,0.2))); //true
//        System.out.println(pTree.contains(new Point2D(0.1, 0.2))); // false
//        System.out.println(pTree.isEmpty()); //false
//        System.out.println(pTree.size()); // 10
//        pTree.draw();
//        for (Point2D p : pTree.range(new RectHV(0.2, 0.2, 0.6, 0.7))) {
//            System.out.print(p + "\t");
//        } // (0.2, 0.2)	(0.3, 0.3)	(0.4, 0.4)	(0.5, 0.5)	(0.6, 0.6)
//        System.out.println();
//        System.out.println(pTree.nearest(new Point2D(0.33, 0.33))); // (0.3, 0.3)
        String filename = args[0];
        In in = new In(filename);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            pTree.insert(p);
        }
        pTree.draw();
        pTree.nearest(new Point2D(0.375, 0.5));
        System.out.println(pTree.nearest(new Point2D(0.375, 0.5)));
    }
}