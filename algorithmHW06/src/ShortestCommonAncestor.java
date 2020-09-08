import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ShortestCommonAncestor{
	public Digraph digraph;
	// cache will contain a hashset with two integer, which is v & w
	// the following int[] has two integer, which is distance between (v,w) and the LCA
	private HashMap<HashSet<Integer>, int[]> cache = new HashMap<>();
	
	public ShortestCommonAncestor(Digraph G) {
		if(G == null) throw new IllegalArgumentException("graph is null");
		digraph = new Digraph(G);
		
	}
	// length of shortest ancestral path between v and w
	public int length(int v , int w) {
		if (v < 0 || w < 0) {
			throw new IllegalArgumentException("input is null");
		}
		HashSet<Integer> key = new HashSet<>();
		key.add(v); key.add(w);
		int ret[] = new int[2];
		// cache optimization
		if (cache.containsKey(key)) {
			ret = cache.get(key);
		}else {
			ret = cacheOperation(v, w);	
		}
		return ret[0];

	}
	// a shortest common ancestor of vertices v and w
	public int ancestor(int v, int w) {
		if (v < 0 || w < 0) {
			throw new IllegalArgumentException("input is null");
		}
		HashSet<Integer> key = new HashSet<>();
		key.add(v); key.add(w);
		int ret[] = new int[2];
		// cache optimization
		if (cache.containsKey(key)) {
			ret = cache.get(key);
		}else {
			ret = cacheOperation(v, w);	
		}
		return ret[1];
	}
	// length of shortest ancestral path of vertex subsets A and B
	public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
		if(subsetA == null|| subsetB == null) { 
			throw new IllegalArgumentException("subset is null");
		}
		int ret[] = new int[2];
		
		ret = cacheOperation(subsetA, subsetB);
		return ret[0];		
	}
	// a shortest common ancestor of vertex subsets A and B
	public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
		if(subsetA == null|| subsetB == null) {
			throw new IllegalArgumentException("subset is null");
		}
		int ret[] = new int[2];
		
		ret = cacheOperation(subsetA, subsetB);
		return ret[1];
		 
	}
	public int[] cacheOperation(int v, int w) {
		 HashSet<Integer> key = new HashSet<>();
		 key.add(v);
		 key.add(w);
		 // build edgeTo, marked[], distancTo[], take O(n^2) time 
		 BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(digraph, v);
		 BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(digraph, w);
		 int distance = Integer.MAX_VALUE;
		 int ancestor = 0;
		 // access every vertex, find the distance between vertex and nouns
		 // the vertex with the least distance the the shortest common ancestor
		 for(int vertex = 0; vertex < digraph.V(); vertex++) {
			 if(vPath.hasPathTo(vertex)&&wPath.hasPathTo(vertex)) {
				 int vwDist = vPath.distTo(vertex)+wPath.distTo(vertex);
				 
				 if(vwDist<distance) {
					 distance = vwDist;
					 ancestor = vertex;
				 }
			 }
		 }
		 if(distance == Integer.MAX_VALUE) {
			 distance = -1;
			 ancestor = -1;
		 }
		 
		 int[] value = new int[] {distance, ancestor};
		 
		 cache.put(key, value);
		 return value;
		 
	}
	public int[] cacheOperation(Iterable<Integer> subSetA, Iterable<Integer> subSetB) {
		 
		 BreadthFirstDirectedPaths APath = new BreadthFirstDirectedPaths(digraph, subSetA);
		 BreadthFirstDirectedPaths BPath = new BreadthFirstDirectedPaths(digraph, subSetB);
		 int distance = Integer.MAX_VALUE;
		 int ancestor = 0;

		 
		 for(int vertex = 0; vertex < digraph.V(); vertex++) {
			 if(APath.hasPathTo(vertex)&&BPath.hasPathTo(vertex)) {
				 int vwDist = APath.distTo(vertex)+BPath.distTo(vertex);
				 if(vwDist<distance) {
					 distance = vwDist;
					 ancestor = vertex;
				 }
			 }
		 }
		 if(distance == Integer.MAX_VALUE) {
			 distance = -1;
			 ancestor = -1;
		 }
		 
		 int[] value = new int[] {distance, ancestor};
		 
		 return value;
	}
	public static void main(String[] args) {
		In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
	    ArrayList<Integer> subsetA  = new ArrayList<>();
	    ArrayList<Integer> subsetB = new ArrayList<>();
	    List<Integer> A = Arrays.asList(10,9,11);
	    List<Integer> B = Arrays.asList(3,6,1,7);
	    subsetA.addAll(A);
	    subsetB.addAll(B);	    
	    
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sca.length(v, w);
	        int ancestor = sca.ancestor(v, w);
	        int subLen = sca.lengthSubset(subsetA, subsetB);
	        int sunAnc = sca.ancestorSubset(subsetA, subsetB);

	        
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	        StdOut.printf(subLen+" "+sunAnc);
	    }	
	}
}