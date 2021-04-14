package algo.part1.week1;

public class QuickUnion {

	private int n;
	private int[] id;
	
	public QuickUnion(int n) {
		this.n = n;
		id = new int[n];
		for(int i=0;i<n;i++)
			id[i] = i;
	}
	
	private int root(int i) {
		
		while(id[i] != i)
			i = id[i];
		return i;
	}
	
	public boolean find(int p, int q) {
		return root(p) == root(q);
	}
	
	public void union(int p, int q) {
		int proot = root(p);
		int qroot = root(q);
		
		id[proot] = qroot;
	}
}
