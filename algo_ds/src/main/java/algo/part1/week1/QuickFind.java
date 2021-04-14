package algo.part1.week1;

public class QuickFind {

	private int n;
	private int[] id;
	
	public QuickFind(int n) {
		this.n = n;
		id = new int[n];
		for(int i=0;i<n;i++)
			id[i] = i;
	}
	
	public void union(int p, int q) {
		
		int pid = id[p];
		int qid = id[q];
		
		for(int i=0;i<n;i++) {
			if(id[i] == pid)
				id[i] = qid;
		}
	}
	
	public boolean connected(int p, int q) {
		return id[p] == id[q];
	}
	
	public int find(int p) {
		return -1;
	}
	
	public int count() {
		return n;
	}
}
