import javax.swing.Box;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class main {

	public static void main(String[] args) {
		SimpleTest();
//    	IOTest();
		testStat();
	}

	public static void SimpleTest() {

		int N = 5;
		Percolation box = new Percolation(N, "WeightQuickUnionUF");

		box.percolates();

		box.open(0, 0);
		box.open(2, 3);
		box.open(1, 4);
		box.open(1, 2);
		box.open(0, 2);

		box.printBroad();
		box.percolates();

		box.open(1, 1);
		box.open(0, 1);

		box.printBroad();
		box.percolates();

		box.open(2, 2);
		box.open(2, 1);
		box.open(3, 1);

		box.printBroad();
		box.percolates();

		box.open(4, 1);

		box.printBroad();
		box.percolates();

		box.open(4, 3);

		box.printBroad();
		box.percolates();

		box.isOpen(2, 3);
		box.isFull(2, 3);

		box.isOpen(4, 2);
		box.isFull(4, 2);

		box.isOpen(4, 3);
		box.isFull(4, 3);

	}

	public static void IOTest() {
		int N = StdIn.readInt();
		Percolation box = new Percolation(N, "QuickUnionUF");

		box.percolates();

		box.open(0, 0);
		box.open(2, 3);
		box.open(1, 4);
		box.open(1, 2);
		box.open(0, 2);

		box.printBroad();
		box.percolates();

		box.open(1, 1);
		box.open(0, 1);

		box.printBroad();
		box.percolates();

		box.open(2, 2);
		box.open(2, 1);
		box.open(3, 1);

		box.printBroad();
		box.percolates();

		box.open(4, 1);

		box.printBroad();
		box.percolates();

		box.open(4, 3);

		box.printBroad();
		box.percolates();

		box.isOpen(2, 3);
		box.isFull(2, 3);

		box.isOpen(4, 2);
		box.isFull(4, 2);

		box.isOpen(4, 3);
		box.isFull(4, 3);

	}

	public static void testStat() {

		while (true) {
			StdOut.println("size of percolation, trials:");

			int N = StdIn.readInt();
			int T = StdIn.readInt();

			StdOut.println("Using QuickFind");
			Stopwatch timer1 = new Stopwatch();
			final PercolationStats ps1 = new PercolationStats(N, T, "QuickFindUF");
			double time1 = timer1.elapsedTime();
			StdOut.println("mean: " + ps1.mean());
			StdOut.println("standard deviation: " + ps1.stddev());
			StdOut.println("95% confidence interval: " + ps1.confidenceHigh() + " " + ps1.confidenceLow());
			StdOut.println("elapse time: " + time1 + "\n");

			StdOut.println("Using WeightQuickUnion");
			Stopwatch timer2 = new Stopwatch();
			final PercolationStats ps2 = new PercolationStats(N, T, "WeightQuickUnionUF");
			double time2 = timer2.elapsedTime();
			StdOut.println("mean: " + ps2.mean());
			StdOut.println("standard deviation: " + ps2.stddev());
			StdOut.println("95% confidence interval: " + ps2.confidenceHigh() + " " + ps2.confidenceLow());
			StdOut.println("elapse time: " + time2);
		}

	}
}
