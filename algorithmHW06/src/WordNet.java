import java.io.File;
import java.util.HashMap;


import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Count;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
public class WordNet{
	
	// under hypernyms.txt, the first number is the lower nouns, all the following number is the upper nouns of lower
	public Digraph wordNet;
	public HashMap<Integer, String> synset;
	public HashMap<String, Bag<Integer>> synMap;
	
	public ShortestCommonAncestor sca;

	// constructor takes the name of the two input files
	//O(n)
	public WordNet(String synsets, String hypernyms){
		if(synsets==null||hypernyms==null) throw new IllegalArgumentException("input is nill");
		synset = new HashMap<Integer, String>();
		synMap = new HashMap<String, Bag<Integer>>();
		
		int number = readSynsets(synsets);
		wordNet = new Digraph(number);
		readHypernyms(hypernyms);
		
		DirectedCycle dc = new DirectedCycle(wordNet);
		if(dc.hasCycle()) throw new IllegalArgumentException("hypernyms has cycle");
		
		sca = new ShortestCommonAncestor(wordNet);
	}
	public class DirectedCycle {
	    private boolean[] marked;        // marked[v] = has vertex v been marked?
	    private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
	    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
	    private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)

	    /**
	     * Determines whether the digraph {@code G} has a directed cycle and, if so,
	     * finds such a cycle.
	     * @param G the digraph
	     */
	    public DirectedCycle(Digraph G) {
	        marked  = new boolean[G.V()];
	        onStack = new boolean[G.V()];
	        edgeTo  = new int[G.V()];
	        for (int v = 0; v < G.V(); v++)
	            if (!marked[v] && cycle == null) dfs(G, v);
	    }

	    // check that algorithm computes either the topological order or finds a directed cycle
	    private void dfs(Digraph G, int v) {
	        onStack[v] = true;
	        marked[v] = true;
	        for (int w : G.adj(v)) {

	            // short circuit if directed cycle found
	            if (cycle != null) return;

	            // found new vertex, so recur
	            else if (!marked[w]) {
	                edgeTo[w] = v;
	                dfs(G, w);
	            }

	            // trace back directed cycle
	            else if (onStack[w]) {
	                cycle = new Stack<Integer>();
	                for (int x = v; x != w; x = edgeTo[x]) {
	                    cycle.push(x);
	                }
	                cycle.push(w);
	                cycle.push(v);
	                assert check();
	            }
	        }
	        onStack[v] = false;
	    }

	    /**
	     * Does the digraph have a directed cycle?
	     * @return {@code true} if the digraph has a directed cycle, {@code false} otherwise
	     */
	    public boolean hasCycle() {
	        return cycle != null;
	    }

	    /**
	     * Returns a directed cycle if the digraph has a directed cycle, and {@code null} otherwise.
	     * @return a directed cycle (as an iterable) if the digraph has a directed cycle,
	     *    and {@code null} otherwise
	     */
	    public Iterable<Integer> cycle() {
	        return cycle;
	    }

	    // certify that digraph has a directed cycle if it reports one
	    private boolean check() {

	        if (hasCycle()) {
	            // verify cycle
	            int first = -1, last = -1;
	            for (int v : cycle()) {
	                if (first == -1) first = v;
	                last = v;
	            }
	            if (first != last) {
	                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
	                return false;
	            }
	        }
	        return true;
	    }
	}
	// all WordNet nouns
	public Iterable<String> nouns(){
		return synMap.keySet();
	}
	// is the word a WordNet noun?
	// O(logn)
	public boolean isNoun(String word) {
		if (word==null) throw new IllegalArgumentException("word is null");
		return synset.containsValue(word);
	}
	// a synset (second field of synsets.txt) that is a shortest common ancestor
	// of noun1 and noun2 (defined below)
	public String sca(String noun1, String noun2) {
		if (noun1 == null|| noun2 == null) throw new IllegalArgumentException("nouns is null");
	
		Bag<Integer> subsetA = synMap.get(noun1);
		Bag<Integer> subsetB = synMap.get(noun2);
		
		int ancID = sca.ancestorSubset(subsetA, subsetB);
		return synset.get(ancID);
		
	}
	// distance between noun1 and noun2 (defined below)
	public int distance(String noun1, String noun2) {
		if (noun1 == null|| noun2 == null) throw new IllegalArgumentException("nouns is null");
		Bag<Integer> subsetA = synMap.get(noun1);
		Bag<Integer> subsetB = synMap.get(noun2);
		
		return sca.lengthSubset(subsetA, subsetB);
	}
	private int readSynsets(String synsets) {
		if(synsets == null) throw new IllegalArgumentException("synset is null");
		In in = new In(synsets);
		int count = 0;
		while(in.hasNextLine()) {
			count++;
			String line = in.readLine();			//  0,'hood,(slang) a neighborhood  
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);	
            synset.put(id, parts[1]);
            String[] nouns = parts[1].split(" ");	//  71,Abkhaz Abkhas,Circassian people living east of the Black Sea  
            for (String n : nouns) {
                if (synMap.get(n) != null) {
                    Bag<Integer> bag = synMap.get(n);
                    bag.add(id);
                } else {
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(id);
                    synMap.put(n, bag);
                }
            }
		}
		return count;
		
	}
	private void readHypernyms(String hypernyms) {
		if(hypernyms == null) throw new IllegalArgumentException("hyper is null");
		In in = new In(hypernyms);
		while(in.hasNextLine()) {
			String line = in.readLine();
			String[] part = line.split(",");
			
			int lower = Integer.parseInt(part[0]);
			
			for(int i = 1; i<part.length; i++) {
				int hyper = Integer.parseInt(part[i]);
				wordNet.addEdge(lower, hyper);
			}
			
		}
	}
	public static void main(String[] args) {
        System.out.println("hello happy world");
        
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");

        String nounA = args[0];
        String nounB = args[1];

        String ancestor = wordnet.sca(nounA, nounB);
        int length = wordnet.distance(nounA, nounB);

        StdOut.println("Ancestor = "+ ancestor+"\nLength = "+length+"\n" );
	
	}
	
}
