import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public final class Board {
    private final int[][] board;
    
    // store hamming, manhattan for cache optimization
	private int hamming = -1;
	private int manhattan = -1;
	private int zero;
	// row_major, help to get inverse number
	public int inversenum = -1;
	private int[] row_major;

	// O(n^2)
    public Board(int[][] board) {
        this.board = deepcopy(board);
        row_major = new int[board.length*board.length-1];
        row_major = row_major(board);
    }
    private int[][] deepcopy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        return copy;
    }
    private int[] row_major(int[][] board) {
    	int [] a = new int[board.length*board.length-1]; 
    	ArrayList<Integer> ret = new ArrayList<>();
    	for(int i = 0; i<board.length;i++) {
    		for(int j = 0;j<board.length;j++) {
    			if(board[i][j]!=0) {
    				ret.add(board[i][j]);
    			}	
    		}
    	}
        for (int i=0; i < ret.size(); i++)
        {
            a[i] = ret.get(i).intValue();
        }
        return a;
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
            	s.append(board[i][j] + "\t");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int dimension() {
        return board.length;
    }
    public int tileAt(int row, int col) {
    	return board[row][col];
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Board that = (Board) obj;
        return Arrays.deepEquals(this.board, that.board);
    }
    // change the value of two tile
    private int[][] swap(int i1, int j1, int i2, int j2) {
        int[][] copy = deepcopy(board);
        int temp = copy[i1][j1];
        copy[i1][j1] = copy[i2][j2];
        copy[i2][j2] = temp;
        return copy;
    }
    // idea is provide from assignment website
    // I use merge sort to get inverse number O(n^2) -> O(nlogn)
	public boolean isSolveable() {
		int evenOdd = board.length%2;
		switch (evenOdd) {
		case 1:	// odd board
			if(getInverseNum(this.row_major, 0, this.row_major.length-1)%2==0) return true;
			else return false;
		case 0:	// even borad
			if((getInverseNum(this.row_major, 0, this.row_major.length-1)+row(zero))%2 == 1) return true;
			else return false;
		}
		return false;
	}
	private int  getInverseNum(int [] arr, int l, int r) {
		int count = 0;
		if(l<r) {
			int m = l+(r-l)/2;
			count += getInverseNum(arr, l, m);
			count += getInverseNum(arr, m+1, r);
			count += mergeAndCount(arr, l, m, r);
			
		}
		this.inversenum = count;
		return count;
	}
	private int mergeAndCount(int [] arr, int l, int m, int r){
		
		 int[] left = Arrays.copyOfRange(arr, l, m + 1); 
		 int[] right = Arrays.copyOfRange(arr, m + 1, r + 1); 
		 int i = 0, j = 0, k = l, swaps = 0;
		 
	        while (i < left.length && j < right.length) { 
	            if (left[i] <= right[j]) 
	                arr[k++] = left[i++]; 
	            else { 
	                arr[k++] = right[j++]; 
	                swaps += (m + 1) - (l + i); 
	            } 
	        } 
	        // Fill from the rest of the left subarray 
	        while (i < left.length) 
	            arr[k++] = left[i++]; 
	        // Fill from the rest of the right subarray 
	        while (j < right.length) 
	            arr[k++] = right[j++]; 
	  
	        return swaps; 
	}
    public boolean isGoal() {
        return hamming() == 0;
    }
    // the number of neighbors is determined by its location 
    // simply change the value of the nearby tile
    public Iterable<Board> neighbors() {
        Stack<Board> neighbours = new Stack<>();
        int position = blankPosition();
        int i = position / dimension();
        int j = position % dimension();
        if (i > 0)
            neighbours.push(new Board(swap(i, j, i - 1, j)));
        if (i < board.length - 1)
            neighbours.push(new Board(swap(i, j, i + 1, j)));
        if (j > 0)
            neighbours.push(new Board(swap(i, j, i, j - 1)));
        if (j < board.length - 1)
            neighbours.push(new Board(swap(i, j, i, j + 1)));

        return neighbours;
    }

    private int blankPosition() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j] == 0)
                    return j + i * dimension();
        return -1;
    }
    // O(n^2) with cache optimization
	public int hamming() {
		if (hamming != -1) {
			return this.hamming;
		}
		int hamming = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == 0)
					continue;
				else if (board[i][j] != index(i, j))
					hamming++;
				this.hamming = hamming;
			}
		}

		return this.hamming;
	}
	// O(n^2) with cache optimization
	public int manhattan() {
		if (manhattan != -1) {
			return manhattan;
		}

		int manhattan = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				
				if (board[i][j] == 0)
					continue;
				else if (board[i][j] != index(i, j)) {

					int place = board[i][j];
					int row = row(place);
					int col = col(place);

					manhattan = manhattan + Math.abs(i - row) + Math.abs(j - col);
				}
				this.manhattan = manhattan;
			}
		}

		return this.manhattan;
	}
	// helping method
	private int row(int index) {
		return (index - 1) / board.length;
	}

	private int col(int index) {
		return ((index - 1) % board.length);
	}
	private int index(int row, int col) {
		return col+board.length*row+1;
	}

	public static void main(String[] args) {

		In in = new In(args[0]);
		int n = in.readInt();
		int[][] block = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				block[i][j] = in.readInt();
			}
		}
		Board init = new Board(block);

		// ******************************
		StdOut.println(init.toString());
		// ******************************
		StdOut.println("(0,1) is: " + init.tileAt(1, 0));
		StdOut.println("(2,1) is: " + init.tileAt(2, 1));
		// ******************************
		StdOut.println("hamming is: " + init.hamming());
		// ******************************
		StdOut.println("manhattan is: " + init.manhattan());
		// ******************************
		StdOut.println("is goal: " + init.isGoal());
		// ******************************
		StdOut.println("neighbor: \n" + init.neighbors());
		// ******************************
		
		StdOut.println("solveable: "+init.isSolveable());
		StdOut.println("inverse: "+init.inversenum);

	}
}