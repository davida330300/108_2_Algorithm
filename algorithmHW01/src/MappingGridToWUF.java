
public class MappingGridToWUF {
	private boolean grid[][];
	private int side_length_of_grid;
	WeightedQuickUnionUF uf;
	
	/**
	 * initialize grid to close, with complexity of n^2
	 * time complexity O(n^2)
	 * @param side_length_of_grid
	 */
	public MappingGridToWUF(int side_length_of_grid) {

		this.side_length_of_grid = side_length_of_grid;
		int size_of_uf = side_length_of_grid * side_length_of_grid + 2;

		this.side_length_of_grid = side_length_of_grid;
		grid = new boolean[side_length_of_grid][side_length_of_grid];
		for (int i = 0; i < side_length_of_grid; i++) {
			for (int j = 0; j < side_length_of_grid; j++) {
				grid[i][j] = false;
			}
		}
		uf = new WeightedQuickUnionUF(side_length_of_grid*side_length_of_grid+2);



	}

	/**
	 * side length = 5
	 * UFindex(0,0); return 1;(0 is the dummy_ceiling)
	 * UFindex(2,0); return 11;
	 * UFindex(2,5); return column out of bound
	 * time complexity O(1)
	 * @param row
	 * @param col
	 * @return
	 */
	public int UFindex(int row, int col) {

		if (row > side_length_of_grid || row < 0) {
			throw new java.lang.IllegalArgumentException("row index out of bound\n");
		}
		if (col > side_length_of_grid || col < 0) {
			throw new java.lang.IllegalArgumentException("column index out of bound\n");
		} else {
			return row * side_length_of_grid + col + 1;
		}

	}
	/**
	 * time complexity O(1)
	 * @param row
	 * @param col
	 * @param status
	 * @return
	 */
	public int UFindex(int row, int col, boolean status) {

		if (!validateGridIndex(row, col)) {
			return 0;
		}
		grid[row][col] = true;
		if (status == true) {
			// left and is open
			if (validateGridIndex(row, col - 1)&&this.grid[row][col - 1]) {

				int left = UFindex(row, col - 1);
				int me = UFindex(row, col);
				uf.union(me, left);
			}
			// right and is open
			if (validateGridIndex(row, col + 1)&&this.grid[row][col + 1]) {
				int right = UFindex(row, col + 1);
				int me = UFindex(row, col);
				uf.union(me, right);
			}
			// up and is open
			if (validateGridIndex(row - 1, col)&&this.grid[row-1][col]) {
				int up = UFindex(row - 1, col);
				int me = UFindex(row, col);
				uf.union(me, up);
			}
			// down and is open
			if (validateGridIndex(row + 1, col)&&this.grid[row+1][col]) {
				int down = UFindex(row + 1, col);
				int me = UFindex(row, col);
				uf.union(me, down);
			}
		}

		return 0;

	}
	/**
	 * time complexity O(1)
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean validateGridIndex(int row, int col) {
		if(row>=0 && row<side_length_of_grid) {
			if(col>=0&&col<side_length_of_grid) {
				return true;
			}
		}
		return false;
	}
	/**
	 * time complexity O(n)
	 * @param name
	 */
	public void UnionDummy(String name) {
		if (name == "dummy_ceiling") {
			int dummy_ceiling = 0;
			for (int i = 1; i <= side_length_of_grid; i++) {
				uf.union(dummy_ceiling, i);
			}
		} else if (name == "dummy_floor") {
			int dummy_floor_uf_index = side_length_of_grid * side_length_of_grid + 1;
			int floor_start_uf_index = side_length_of_grid * (side_length_of_grid - 1)+1;
			int floor_end_uf_index = side_length_of_grid * side_length_of_grid;
			for (int i = floor_start_uf_index; i <= floor_end_uf_index; i++) {
				uf.union(dummy_floor_uf_index, i);
			}
		}
	}

}

