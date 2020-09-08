

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	public String uFType;
	private double[] opened;
	private int trials;
	private int n;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials,String UFType){
    	if(n<=0||trials<=0) {
    		 throw new IllegalArgumentException("input should be > 0");
    	}
    	this.trials = trials;
    	this.n = n;
    	
    	opened = new double[trials];
    	
    	for(int i = 0; i<trials;i++) {
    		
    		Percolation boxPercolation = new Percolation(n, UFType);
    		
    		double count = 0;
            while (!boxPercolation.percolates()) {
                int row = StdRandom.uniform(0, n);
                int column = StdRandom.uniform(0, n);
                if (!boxPercolation.isOpen(row, column)) {
                	boxPercolation.open(row, column);
                    count++;
                }
            }
            double size_of_grid = n * n;
            opened[i] = count / size_of_grid;
    	}

    }

    // sample mean of percolation threshold
    public double mean(){
    	return StdStats.mean(opened);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
    	return StdStats.stddev(opened);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow(){
       return mean()-((1.96*Math.sqrt(stddev()))/Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh(){
    	return mean()+((1.96*Math.sqrt(stddev()))/Math.sqrt(trials));
    }
}