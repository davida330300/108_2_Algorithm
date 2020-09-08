
public class QuickFindUF {
		private int[] id;     // access to component id (site indexed)
	   private int count;    // number of components
	   public QuickFindUF(int N)
	   {  // Initialize component id array.
	      count = N;
	      id = new int[N];
	      for (int i = 0; i < N; i++)
	         id[i] = i;
	   }
	   public int count()
	   {  return count;  }
	   public boolean connected(int p, int q)
	   {  return find(p) == find(q);  }
	   public int  find(int p) {
		   return id[p];
	   }
	   public void union(int p, int q) 
	   {  // Put p and q into the same component.
	      int pID = find(p);
	      int qID = find(q);

	      if (pID == qID) return;
	      // Rename p’s component to q’s name.
	      for (int i = 0; i < id.length; i++)
	          if (id[i] == pID) id[i] = qID;
	      count--; 
	   }
}
