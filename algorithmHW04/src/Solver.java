import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public final class Solver {
    private final ArrayList<Board> solutionBoards;
    private boolean isSolvable;
    private Board board;
    
    
    private class Node implements Comparable<Node> {
        private final Board board;
        private final Node prevNode;
        // the step that had used
        private int moves;
        // select between hamming() or manhattan()
        private int code;

        public Node(Board board, Node prevNode) {
            this.board = board;
            this.prevNode = prevNode;
            this.code = board.manhattan();
            if (prevNode != null) moves = prevNode.moves + 1;
            else moves = 0;
        }
        // the key represent the priority as the candidate of next step
        private Integer getKey() {
        	return this.moves+this.code;
        }
        private Integer getDist() { 
        	return this.code;
        }
        @Override
        // a override compareTo for using key and distance to compare in min priority queue 
        public int compareTo(Node other) {
            if (this.getKey().compareTo(other.getKey())==0) return this.getDist().compareTo(other.getDist());
            else return this.getKey().compareTo(other.getKey());
        }
    }
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        this.board = initial;
        isSolvable = false;
        solutionBoards = new ArrayList<Board>();
        MinPQ<Node> searchNodes = new MinPQ<>();

        //**********************
        // A* algorithm
        searchNodes.insert(new Node(initial, null));

        while (!searchNodes.min().board.isGoal()) {
            Node searchNode = searchNodes.delMin();
            for (Board board : searchNode.board.neighbors())
            	// prevent from moving to the last state, optimization
                if (searchNode.prevNode==null||!searchNode.prevNode.board.equals(board))
                    searchNodes.insert(new Node(board, searchNode));
        }
        //**********************
        
        Node current = searchNodes.min();
        // building solution sequence
        while (current.prevNode != null) {
            solutionBoards.add(current.board);
            current = current.prevNode;
        }
        solutionBoards.add(current.board);
        
        if (current.board.equals(initial)) isSolvable = true;

    }
    public int moves() {
        if (!isSolvable()) return -1;
        return solutionBoards.size() - 1;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) return solutionBoards;
        return null;
    }

    private boolean isSolvable() {
        return isSolvable;
    }

    public static void main(String[] args) {
        // create initial board from file
    	In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();

        StdOut.println("hello happy world");
        
        Board init = new Board(blocks);	
        
        if(init.isSolveable()) {
            Stopwatch timer = new Stopwatch();
            Solver solver = new Solver(init);
        	double time = timer.elapsedTime();
      
            StdOut.println("Minimum number of moves = " + solver.moves());
            
            for (Board board : solver.solution())        
            	StdOut.println(board);
            
            StdOut.println(time);
        
        }else {
        	StdOut.println("The puzzle cannot be solved");
        }
        
        
    }


}
