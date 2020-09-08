import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdOut;

public class BinarySearchDeluxe {
    
	private Term[] thisterm;
	/**
     * time complexity: 1+logn
     * Returns the index of the first  key in the sorted array a[]
     * that is equal to the search key, or -1 if no such key.
     * 
     * add an additional condition to find last element of certain key
     * the high will iterate if the previous element of middle has same key
     * 
	 * @param <Key>
	 * @param a
	 * @param key
	 * @param comparator
	 * @return
	 */
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
		if (a == null || key == null || comparator == null) {
			throw new IllegalArgumentException("null argument \n");
		}
		
		
		
		// Arrays.sort(a, comparator);
		/**
		 * In 
		 */
		
		int low = 0;
		int high = a.length-1;
		int mid;
		
		while(low<=high) {
			mid = low + (high - low)/2;
			
			if(comparator.compare(key, a[mid]) < 0) high = mid-1;
			else if (comparator.compare(key, a[mid]) > 0) low = mid+1;
			else if (comparator.compare(a[mid-1], key)==0) high = mid -1;
			else return mid;
		}
		
		
		return -1;
		
    }

    /**
     * time complexity: 1+logn
     * Returns the index of the last key in the sorted array a[]
     * that is equal to the search key, or -1 if no such key.
     * 
     * add an additional condintion to find last element of certain key
     * the low will iterate if the next element of middle has same key
     * 
     * @param <Key>
     * @param a
     * @param key
     * @param comparator
     * @return
     */
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
		if (a == null || key == null || comparator == null) {
			throw new IllegalArgumentException("null argument \n");
		}
		
//		Arrays.sort(a, comparator);
		
		
		int low = 0;
		int high = a.length-1;
		int mid = 0;
		
		while(low<=high) {
			mid = low + (high - low)/2;
			
			if(comparator.compare(key, a[mid]) < 0) high = mid-1;
			else if (comparator.compare(key, a[mid]) > 0) low = mid+1;
			else if (comparator.compare(a[mid+1], key)==0) low = mid +1;
			else return mid;

		}


		return -1;
    	
    }
    /**
     * 
     * @param args
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws IllegalAccessException {
    	Term[] term = {
				new Term("Saten Ruiko", 13),	// query, weight
				new Term("Saten Ruako", 13),
				new Term("Saten Ruok", 13),
				new Term("Uiharu Kazari",15),
				new Term("Misaka Mikoto",22),
				new Term("Shirai Kuroko",7),
				new Term("Kamijo Toma",27),
				new Term("Accelerator",11),
				new Term("Syokuho Misaki",16),	
				new Term("Hamazura Shiage", 33),
				new Term("Index", 21),
				new Term("Takitsubo Rikou",100)
	 	  	};   	
    	Arrays.sort(term, Term.byReverseWeightOrder());
    	Term test = new Term("Saten Ruok", 13);
    	StdOut.println(firstIndexOf(term, test, Term.byReverseWeightOrder()));
    	StdOut.println(lastIndexOf(term, test, Term.byReverseWeightOrder()));
    	StdOut.println("**********");
    	for(Term i :term ) {
    		StdOut.println(i);
    	}
    	StdOut.println("\n");
    	Arrays.sort(term, Term.byPrefixOrder(3));
    	StdOut.println(firstIndexOf(term, test, Term.byPrefixOrder(3)));
    	StdOut.println(lastIndexOf(term, test, Term.byPrefixOrder(3)));
    	StdOut.println("**********");
    	for(Term i :term ) {
    		StdOut.println(i);
    	}
    	
    	
    }
}
