import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.Quick3way;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
public class Autocomplete {
	
	private Term[] thisterm;
    
	// 
	/**
	 * Initializes the data structure from the given array of terms.
	 * use merge sort to sort the element in lexicographic order in advance
	 * so that we can use binary search in allmatch() & numberofmatch() operation
	 * @param terms
	 */
    public Autocomplete(Term[] terms) {
    	this.thisterm = terms;
    	Merge.sort(thisterm);
    }

    // 
    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * 
     * we must find the first index of certain prefix element, so that we can copy element from thisterm to all_match_term
     * @param prefix
     * @return
     */
    public Term[] allMatches(String prefix) {
    	if(prefix == null) throw new IllegalArgumentException();
		Term test = new Term(prefix, 0);	// 
		int N = prefix.length();
		
		int first_index = BinarySearchDeluxe.firstIndexOf(thisterm, test, Term.byPrefixOrder(N));
    	
		int num = numberOfMatches(prefix);
		
		if(num == -1) {		
			throw new NullPointerException("no such element");
		}
		Term[] all_match_result = new Term[num];
		for(int i = 0; i<num;i++) {
			all_match_result[i] = thisterm[first_index+i];
		}
		
		MergeX.sort(all_match_result, Term.byReverseWeightOrder());
		
		return all_match_result;
    }

    // Returns the number of terms that start with the given prefix.
    /**
     * 
     * @param prefix
     * @return
     */
    public int numberOfMatches(String prefix) {
    	if(prefix == null) throw new IllegalArgumentException();
		Term test = new Term(prefix, 0);	// 
		int N = prefix.length();
		int result = -1;
		int first_index = BinarySearchDeluxe.firstIndexOf(thisterm, test, Term.byPrefixOrder(N));
		int last_index = BinarySearchDeluxe.lastIndexOf(thisterm, test, Term.byPrefixOrder(N));
		
		if(first_index==-1||last_index==-1) {
			return result;
		}
		else {
			result =(last_index - first_index +1);		
		}
		
		return result;
    }
    
    public static void main(String[] args) throws IllegalAccessException {

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }
        
        StdOut.println("finish");
        
        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        
        while (StdIn.hasNextLine()) {
        	Stopwatch timer = new Stopwatch();
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            
            
            StdOut.printf("%d matches \n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
            
            double time = timer.elapsedTime();
            StdOut.println(time);
            

        }

    }
}
