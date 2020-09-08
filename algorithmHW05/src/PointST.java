import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointST<Value> {
	
	// key value pair, use point to 
	private RedBlackBST<Point2D,Value> pointSet;
	public int count;
	/**
	 * construct an empty symbol table of points 
	 */
    public PointST() {
    	this.pointSet = new RedBlackBST<Point2D,Value>();
    }
    /**
     * is the symbol table empty? 
     * @return
     */
    public boolean isEmpty() {
		return size() == 0;
    }
    /**
     * O(1)
     * number of points
     * @return
     */
    public int size() {
		return pointSet.size();
    }
    /**
     * O(logn)
     * associate the value val with point p
     * @param p
     * @param val
     */
    public void put(Point2D p, Value val) {
    	if(p == null) {
    		throw new IllegalArgumentException("Point2D p is null!");
    	}
    	pointSet.put(p, val);
    }


    /**
     * O(logn)
     * value associated with point p 
     * @param p
     * @return
     */
    public Value get(Point2D p) {
    	if(p == null||!pointSet.contains(p)) {
    		throw new IllegalArgumentException("Point2D");
    	}
    	return pointSet.get(p);
    	
    }

    // 
    /**
     * O(logn)
     * does the symbol table contain point p? 
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
    	if(p == null) {
    		throw new IllegalArgumentException("Point2D");
    	}
    	return pointSet.contains(p);
    	
    }

    /**
     * O(n)
     * all points in the symbol table
     * @return
     */
    public Iterable<Point2D> points(){
		return pointSet.keys();
    }

    
    /**
     * O(n)
     * however, contain operation use (logn) time -> don't use
     * all points that are inside the rectangle (or on the boundary) 
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect){
    	if(rect == null) {
    		throw new IllegalArgumentException("Point2D");
    	}
    	ArrayList<Point2D> list = new ArrayList<>();
    	// simply test all the point, x(), xmin()... all have O(1) time complexity
    	for(Point2D p : pointSet.keys()) {
    		if (p.x()<=rect.xmax() && p.x()>=rect.xmin()&&p.y()<=rect.ymax() && p.y()>=rect.ymin()) {
    			list.add(p);
    		}
    	}
    	return list;
    	
    }

    /**
     * O(n)
     * a nearest neighbor of point p; null if the symbol table is empty 
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		if (size()==0) {
			return null;
		}
		double distance = Double.MAX_VALUE;
		Point2D minPoint = pointSet.max();
		for(Point2D other : pointSet.keys()) {
			count++;
			if(p.distanceTo(other)<distance) {
				distance = other.distanceSquaredTo(p);
				minPoint = other;
			}
		}
		return minPoint;
		
    	
    }

    // unit testing (required)
    public static void main(String[] args) {
    	StdOut.println("Hello, world!");
    	final int trial = 100;
    	int[] callTime = new int[trial];
        String filename = args[0];
        In in = new In(filename);
        PointST<Integer> brute = new PointST<>();
        int i = 0;
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);            
            brute.put(p, i);
            i++;
        }
        Stopwatch timer = new Stopwatch();
        for(int n = 0; n< trial;n++) {
        	Random rand = new Random(); 
        	double x = rand.nextDouble(); 
            double y = rand.nextDouble(); 
            
            Point2D target = new Point2D(x, y);
            brute.nearest(target);
            callTime[n] = brute.count;
            brute.count = 0;
//          StdOut.println(kd.nearest(target));
            
        }

//      StdOut.println(kd.range(new RectHV(0.4, 0.4 , 0.6, 0.6)));
        double time = timer.elapsedTime();
        StdOut.println(time);
        double[] ret = new double[2];
        ret = calculate(callTime);
        StdOut.println("mean hit of nearest:"+ret[0]+" stdev:"+ret[1]);
    }
	private void draw() {
		// TODO Auto-generated method stub
		 for (Point2D p : pointSet.keys()) {
	            p.draw();
	     }
	}
    public static double[] calculate(int numArray[])
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for(int num : numArray) {
            sum += num;
        }

        double mean = sum/length;

        for(int num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        
        double ret[] = new double[2];
        ret[0] = mean;
        ret[1] = Math.sqrt(standardDeviation/length);

        return ret;
    }

}
