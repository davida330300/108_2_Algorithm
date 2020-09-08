import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdArrayIO;
import java.lang.*;



public class Percolation {
	
	
	private int side_length_of_grid;
	private boolean grid[][];	
	private int num_of_open;
	private String UFType = "";
	
	private int dummy_ceiling_uf_index;	// the First index in UF
	private int dummy_floor_uf_index;	// the Last index in UF
	
	private MappingGridToUF mapUf;
	private MappingGridToUF mapUfForIsFull;
	
	private MappingGridToWUF mapWUf;
	private MappingGridToWUF mapWUfForIsFull;
	
	/**
	 * creates n-by-n grid, with all sites initially blocked
	 * should have time complexity of n^2
	 * @param n
	 */
    public Percolation(int n, String UFType){
    	if (n<=0) {
    		throw new java.lang.IllegalArgumentException(
    				"size of grid should be positive\n");
    	}
    	
    	this.side_length_of_grid = n;
    	this.UFType = UFType;
    	// initialize grid to close, with complexity of n^2
    	grid = new boolean[n][n];
    	for(int i = 0; i < n; i++) {
    		for(int j = 0; j < n; j++) {
    			grid[i][j] = false;
    		}
    	}
    	if(UFType=="QuickFindUF") {
        	mapUf = new MappingGridToUF(side_length_of_grid);
        	mapUf.UnionDummy("dummy_ceiling");
        	mapUf.UnionDummy("dummy_floor");
        	mapUfForIsFull = new MappingGridToUF(side_length_of_grid);
        	mapUfForIsFull.UnionDummy("dummy_ceiling");
        	num_of_open = 0;    		
    	}else if(UFType=="WeightQuickUnionUF") {
        	mapWUf = new MappingGridToWUF(side_length_of_grid);
        	mapWUf.UnionDummy("dummy_ceiling");
        	mapWUf.UnionDummy("dummy_floor");
        	mapWUfForIsFull = new MappingGridToWUF(side_length_of_grid);
        	mapWUfForIsFull.UnionDummy("dummy_ceiling");
        	num_of_open = 0;   
    	}

    }



	/**
	 * time complexity O(1)
     * opens the site (row, col) if it is not open already
     * @param row
     * @param col
     */
    public void open(int row, int col){

    	if(!validateGridIndex(row, col)) {
    		return;
    	}
    	
    	if(grid[row][col]==false) {
        	grid[row][col] = true;
        	num_of_open++;
        	if(this.UFType=="QuickFindUF") {
            	mapUf.UFindex(row, col, true);
            	mapUfForIsFull.UFindex(row, col, true);        		
        	}else if(this.UFType =="WeightQuickUnionUF") {
            	mapWUf.UFindex(row, col, true);
            	mapWUfForIsFull.UFindex(row, col, true);  
        	}

    	}else {}

    	
        return;
    }

    /**
     * time complexity O(1)
     * is the site (row, col) open?
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col){
    	
    	if(!validateGridIndex(row, col)) {
    		return false;
    	}
    	
    	if(grid[row][col]==true) {
    	//	StdOut.println("is open");
    		return true;
    	}else {
    	//	StdOut.println("is close");
    		return false;
    	}
    }

    /**
     * time complexity O(1)
     * is the site (row, col) full?
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col){
    	dummy_ceiling_uf_index = 0;
    	if(!validateGridIndex(row, col)) {
    		return false;
    	}
    	if(this.UFType == "QuickFindUF") {
        	int me = mapUfForIsFull.UFindex(row, col);
        	if(mapUfForIsFull.uf.connected(dummy_ceiling_uf_index, me)) {
        	//	StdOut.println("is full");
                return true;    		
        	}else {
        	//	StdOut.println("is empty");
        		return false;
        	}
    	}else if(this.UFType=="WeightQuickUnionUF") {
    		int me = mapWUfForIsFull.UFindex(row, col);
        	if(mapWUfForIsFull.uf.connected(dummy_ceiling_uf_index, me)) {
        	//	StdOut.println("is full");
                return true;    		
        	}else {
        	//	StdOut.println("is empty");
        		return false;
        	}
    	}
    	return false;


    }

    /**
     * time complexity O(1)
     * returns the number of open sites
     * @return
     */
    public int numberOfOpenSites(){
        return num_of_open;
    }
    
    public boolean validateGridIndex(int row, int col) {
    	if(row>side_length_of_grid-1 || row<0) {
    		throw new java.lang.IllegalArgumentException(""
    				+ "row index out of bound\n");
    	}
    	if(col>side_length_of_grid-1 || col<0) {
    		throw new java.lang.IllegalArgumentException(""
    				+ "column index out of bound\n");
    	}else {
    		return true;
    	}
    }

    /**
     * does the system percolate?
     * @return
     */
    public boolean percolates(){ 
    	dummy_ceiling_uf_index = 0;
    	dummy_floor_uf_index = side_length_of_grid*side_length_of_grid+1;
    	if(this.UFType == "QuickFindUF") {
    		return mapUf.uf.connected(dummy_ceiling_uf_index, dummy_floor_uf_index);
    	}else if (this.UFType == "WeightQuickUnionUF"){
    		if((mapWUf.uf.connected(dummy_ceiling_uf_index,dummy_floor_uf_index))){
    		//	StdOut.println("is percolation\n");
    		}else {
    		//	StdOut.println("is not percolation\n");
    		}
    		return mapWUf.uf.connected(dummy_ceiling_uf_index, dummy_floor_uf_index);    		
    	}
    	return false;
		

    }
    
    public void printBroad() {
    	StdArrayIO.print(grid);
    }
}
