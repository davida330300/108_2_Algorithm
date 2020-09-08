import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
/**
 * find the less related 
 *
 */
public class Outcast {

    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
  	
        String outcast = null;
        int d = 0;
        // find the nouns with the largest distance with other nouns
        for (int i = 0; i < nouns.length; i++) {
            int dist = distanceWithOthers(nouns[i], nouns);
            if (d < dist) {
                d = dist;
                outcast = nouns[i];
            }
        }
        return outcast;
    }
    // add one by one
    private int distanceWithOthers(String noun, String[] nouns) {
        int distance = 0;
        for (String n : nouns) {
            distance += wordnet.distance(noun, n);
        }
        return distance;
    }
    // unit test
    public static void main(String[] args) {
    	// use command argument, the first and second input is synsets.txt & hypernyms.txt
    	System.out.println("hello happy world");
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        // the following input are outcast file
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}