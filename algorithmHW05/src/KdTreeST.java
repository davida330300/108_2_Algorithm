import java.util.ArrayList;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.Random;

public class KdTreeST<Value> {
	private enum LINES {VERTICAL,HORIZONTAL};
	private int size;
	private Node root;
	public int count = 0;
	
	private class Node{
		private Node parent;
		private Node rightChild;
		private Node leftChild;
		private Point2D point;
		private LINES line;
		private Value val;
		
		Node(Point2D p, Value val, LINES line){
			this.point = p;
			this.val = val;
			this.line = line;	
		}
	}
    public KdTreeST() {
    	size = 0;
    	root = null;
    }
    public boolean isEmpty() {
    	return size ==0;
    }
    public int size() {
    	return size;
    }
    /**
	 * Verified
	 * the operation is implement in insert() method
     * @param p
     * @param val
     * @throws IllegalAccessException
     */
    public void put(Point2D p, Value val) throws IllegalAccessException {
    	if (p ==null) throw new IllegalAccessException("argument is null");
    	if(size==0) {
    		Node root = new Node(p,val,LINES.VERTICAL);
    		this.root = root;
    	}else {
        	insert(root, p, val, LINES.VERTICAL);    
    	}
    	size++;
    }
    /**
     * Verified
     * @param par
     * @param p
     * @param val
     * @param lines
     */
	public void insert(Node par, Point2D p, Value val, LINES lines) {
    	double x = p.x();
    	double y = p.y();
    	double rx = par.point.x();
    	double ry = par.point.y();
  
    	// find where we should put the new node
    	// the root has vertical line
    	
    	if(par.line == LINES.VERTICAL) {
    		if(x<rx) {
    			if(par.leftChild==null) {
    				Node add = new Node(p,val,LINES.HORIZONTAL);
    				// build connection between child and parent
    				par.leftChild = add;
    				add.parent = par;
    			}else {
    				// use recursive to access the lower level
    				insert(par.leftChild, p, val, LINES.HORIZONTAL);
    			}
    		}
    		else if(x>rx||y!=ry) {
    			if(par.rightChild==null) {
    				Node add = new Node(p,val,LINES.HORIZONTAL);
    				par.rightChild = add;
    				add.parent = par;
    			}else {
    				insert(par.rightChild, p, val, LINES.HORIZONTAL);
    			}
    		}
    	}
    	else if(par.line == LINES.HORIZONTAL) {

    		if(y<ry) {
    			if(par.leftChild==null) {
    				Node add = new Node(p,val,LINES.VERTICAL);
    				par.leftChild = add;
    				add.parent = par;
    			}else {
    				insert(par.leftChild, p, val, LINES.VERTICAL);
    			}
    		}	
    		else if(y>ry||x!=rx) {
    			if(par.rightChild==null) {
    				Node add = new Node(p,val,LINES.VERTICAL);
    				par.rightChild = add;
    				add.parent = par;
    			}else {
    				insert(par.rightChild, p, val, LINES.HORIZONTAL);
    			}
    		}
    	}

    }
	/**
	 * Verified
	 * the operation is implement in insert() method
	 * @param p
	 * @return
	 */
    public Value get(Point2D p) {
    	if(p == null) return null;
    	Value ret = get(root,p);
    	return ret;
    }
    /**
     * Verified O(logn)
     * @param par
     * @param p
     * @return
     */
    private Value get(Node par, Point2D p) {
    	if(par == null) return null;
    	
    	double x = p.x();
    	double y = p.y();
    	double rx = par.point.x();
    	double ry = par.point.y();    	
    	
    	if(x==rx&&y==ry) { 
    		return par.val;
    	}
    	else if(par.line == LINES.VERTICAL) {
    		// use recursive to access lower level node
    		if(x>rx) return get(par.rightChild,p);
    		else if(x<rx) return get(par.leftChild,p);
    		else if(y!=ry) return get(par.rightChild, p);
    	}
    	else if(par.line == LINES.HORIZONTAL) {
    		if(y>ry) return get(par.rightChild,p);
    		else if(y<ry) return get(par.leftChild,p);
    		else if(x != rx) return get(par.rightChild, p);
    	} 
    	return null;
    }
    /**
     * Verified 
     * the operation is implement in contain() method
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
    	if(p == null) throw new IllegalArgumentException("Point2D is null");
    	return contains(root, p);
    }
    /**
     * Verified O(logn)
     * @param par
     * @param p
     * @return
     */
    private boolean contains(Node par, Point2D p) {
    	if(par == null) return false;
    	while(par!= null) {
    		if(par.line == LINES.VERTICAL) {
    			// use recursive to access lower level node
    			if(p.x()> par.point.x()) par = par.rightChild;
    			else if(p.x() < par.point.x()) par = par.leftChild;
    			else if(p.y()!=par.point.y()) par = par.rightChild;
    			else return true;
    		}else if(par.line == LINES.HORIZONTAL) {
    			if(p.y()> par.point.y()) par = par.rightChild;
    			else if(p.y() < par.point.y()) par = par.leftChild;
    			else if(p.x()!=par.point.x()) par = par.rightChild;
    			else return true;
    		}
    	}
    	return false;

    }
    /**
     * Verified
     * level order traversal with helping of queue
     * @return
     */
    public Iterable<Point2D> points(){
    	ArrayList<Node> q = new ArrayList<>();
    	q.add(root);
    	ArrayList<Point2D> ret = new ArrayList<>();
    	
    	while(!q.isEmpty()) {
    		Node curr = q.get(0);
    		q.remove(0);
    		ret.add(curr.point);
    		if (curr.leftChild != null) {
    			q.add(curr.leftChild);
    		}
    		if (curr.rightChild != null) {
    			q.add(curr.rightChild);
    		}
    	}
    	return ret;
    }
    //the operation is implement in range method
    public Iterable<Point2D> range(RectHV rect){
    	if (rect == null) throw  new IllegalArgumentException("rect is null");
    	ArrayList<Point2D> ret = new ArrayList<>();
    	RectHV base = new RectHV(0.0, 0.0, 1.0, 1.0);
    	ret = (ArrayList<Point2D>) range(root, base, rect, ret);
    	return ret;
    }
    // use recursive method to test every point 
    private Iterable<Point2D> range(Node par, RectHV base, RectHV rect, ArrayList<Point2D> ret) {
    	
    	if(par == null) throw new IllegalArgumentException("node is null");
    	if(base.intersects(rect)==false) return null;
    	
    	if(rect.contains(par.point))
    		ret.add(par.point);
    	// if the rect intersect with any line?
    	if(par.line == LINES.VERTICAL) {
            if(par.rightChild!= null) {
            	range(par.rightChild, base, rect, ret);
            }
            if(par.leftChild!=null) {
            	range(par.leftChild, base, rect, ret); 
            }		    		
    	}else if(par.line == LINES.HORIZONTAL) {

            if(par.rightChild!= null) {
            	range(par.rightChild, base, rect, ret);
            }
            if(par.leftChild!=null) {
            	range(par.leftChild, base, rect, ret); 
            }  
    	}
    	return ret;
    	
    	
    }
    // the operation is implement in nearest() method
    public Point2D nearest(Point2D p) {
    	Node nearestN = new Node(root.point, root.val, root.line);
        nearestN.leftChild = root.leftChild;
        nearestN.rightChild = root.rightChild;

       
        RectHV rootRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        nearest(root, rootRect, nearestN, p);
        return nearestN.point;
    	
    }
    // the nearest method is explained in the readme document
    private void nearest(Node par, RectHV hRect, Node nearestN, Point2D p) {
    	
        if (par == null) return;

        if (p.distanceSquaredTo(par.point) < p.distanceSquaredTo(nearestN.point)) {
            nearestN.point = par.point;
        }
        double hx = par.point.x();
        double hy = par.point.y();
        double x = p.x();
        double y = p.y();
        double xmin, xmax, ymin, ymax;
        if (par.line == LINES.VERTICAL) {
        	ymin = hRect.ymin();
        	ymax = hRect.ymax();
        	xmin = hRect.xmin();
        	xmax = hx;
        	RectHV leftSide = new RectHV(xmin, ymin, xmax, ymax);

        	xmin = hx;
            xmax = hRect.xmax();
            RectHV rightSide = new RectHV(xmin, ymin, xmax, ymax);
        	
            if (x >= hx) {
                nearest(par.rightChild, rightSide, nearestN, p);
                if (leftSide.distanceSquaredTo(p) < p.distanceSquaredTo(nearestN.point)) {
                    nearest(par.leftChild, leftSide, nearestN, p);
                }
            } else {

                nearest(par.leftChild, leftSide, nearestN, p);
                if (rightSide.distanceSquaredTo(p) < p.distanceSquaredTo(nearestN.point)) {
                    nearest(par.rightChild, rightSide, nearestN, p);
                }
            }
        } else {
            xmin = hRect.xmin();
            xmax = hRect.xmax();
            ymin = hRect.ymin();
            ymax = hy;
            RectHV lower = new RectHV(xmin, ymin, xmax, ymax);
            
            ymin = hy;
            ymax = hRect.ymax();
            RectHV upper = new RectHV(xmin, ymin, xmax, ymax);
            
            
            if (y >= hy) {
                nearest(par.rightChild, upper, nearestN, p);
                if (lower.distanceSquaredTo(p) < p.distanceSquaredTo(nearestN.point)) {
                    nearest(par.leftChild, lower, nearestN, p);
                }
            } else {
                nearest(par.leftChild, lower, nearestN, p);
                if (upper.distanceSquaredTo(p) < p.distanceSquaredTo(nearestN.point)) {
                    nearest(par.rightChild, upper, nearestN, p);
                }
            }
        }
        count++;
    }

    public static void main(String[] args) throws IllegalAccessException {
    	StdOut.println("Hello, world!");
    	final int trial = 100;
    	int[] callTime = new int[trial];
        String filename = args[0];
        In in = new In(filename);
        KdTreeST<Integer> kd = new KdTreeST<>();
        int i = 0;
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);            
            kd.put(p, i);
            i++;
        }
        Stopwatch timer = new Stopwatch();
        for(int n = 0; n< trial;n++) {
        	Random rand = new Random(); 
        	double x = rand.nextDouble(); 
            double y = rand.nextDouble(); 
            
            Point2D target = new Point2D(x, y);
//            kd.nearest(target);
            Point2D a = kd.nearest(target);
            StdOut.println(a+"hit: "+kd.count);
            callTime[n] = kd.count;
            kd.count = 0;

            
        }

//      StdOut.println(kd.range(new RectHV(0.4, 0.4 , 0.6, 0.6)));
        double time = timer.elapsedTime();
        StdOut.println(time);
        double[] ret = new double[2];
        ret = calculate(callTime);
        StdOut.println("mean hit of nearest:"+ret[0]+" stdev:"+ret[1]);
        
		
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
