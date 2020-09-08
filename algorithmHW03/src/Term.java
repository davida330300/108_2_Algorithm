
import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdOut;

public class Term implements Comparable<Term> {
	
	private String query;
	private long weight;
	
	/**
	 * Initializes a term with the given query string and weight.
	 * 
	 * @param query
	 * @param weight2
	 * @throws IllegalArgumentException
	 */
    public Term(String query, long weight2) {
    	if(query==null) {
    		throw new IllegalArgumentException("query is null");
    	}else if(weight2 < 0){
    		throw new IllegalArgumentException("weight should > 0");
    	}
    	
    	this.query = query;
    	this.weight = weight2;
    }

    
    /**
     * Compares the two terms in descending order by weight.
     * @return
     */
    public static Comparator<Term> byReverseWeightOrder(){
		return new ReverseOrderComparator();
    }

    /**
     * 
     * @author CHU
     *
     */
	private static class ReverseOrderComparator implements Comparator<Term>{

		@Override
		public int compare(Term o1, Term o2) {
			// TODO Auto-generated method stub
			if(o1.weight>o2.weight) {return -1;}
			else if(o1.weight<o2.weight) {return 1;}
			else return 0; 
		}
    }

    
    /**
     * Compares the two terms in lexicographic order,
     * but using only the first r characters of each query.
     * @param r
     * @return
     */
    public static Comparator<Term> byPrefixOrder(int r){
		return new PrefixOrderComparator(r);
    	
    }
    /**
     * determine min(str1, str2, prefix r)
     * use the smallest one to compare, use substring to extract element from original string
     * 
     * @author CHU
     *
     */
    private static class PrefixOrderComparator implements Comparator<Term> {
    	private int r;
    	public PrefixOrderComparator(int r) {
    		this.r = r;
    	}

		@Override
		public int compare(Term o1, Term o2) {
			// TODO Auto-generated method stub
			String str1 = o1.query;
			String str2 = o2.query;
			int compare_length = r;
			
			if(str1.length()<r||str2.length()<r) {
				if(str1.length()<str2.length()) {compare_length = str1.length();}
				else compare_length = str2.length();
			}
			
			return str1.substring(0, compare_length).compareTo(str2.substring(0, compare_length));
		}
    }

    
    /**
     * Compares the two terms in lexicographic order by query.
     * 
     * @param that
     * @return
     */
    public int compareTo(Term that) {
    	return this.query.compareTo(that.query);
    }


    /**
     * Returns a string representation of this term in the following format:
     * the weight, followed by a tab, followed by the query.
     */
    public String toString() {
    	return weight+"\t"+query;
    }

	public static void main(String[] args) throws IllegalAccessException {
    	Term[] term = {
    				new Term("Saten Ruiko", 13),	// query, weight
    				new Term("Saten Ruako", 12),
    				new Term("Saten Ruok", 14),
    				new Term("Uiharu Kazari",15),
    				new Term("Misaka Mikoto",22),
    				new Term("Shirai Kuroko",7),
    				new Term("Kamijo Toma",27),
    				new Term("Accelerator",11),
    				new Term("Syokuho Misaki",13),	
    				new Term("Index", 2)
		 	  	};
    	
    	StdOut.println("************************");
    	StdOut.println("sort by reverse weight \n");
    	//Arrays.sort(term, Term.byReverseWeightOrder());
    	Arrays.sort(term, Term.byReverseWeightOrder());
    	for(Term i : term ) {
    		StdOut.println(i);
    	}
    	StdOut.println("************************");
    	StdOut.println("sort by prefix order \n");
    	Arrays.sort(term, Term.byPrefixOrder(6));
    	for(Term i : term ) {
    		StdOut.println(i);
    	}
    }

}

