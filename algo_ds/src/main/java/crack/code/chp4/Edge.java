package crack.code.chp4;

/**
 * 
 * @author vivek
 *
 */
public class Edge {
	private Vertex orig;
	private Vertex dest;
	public Vertex getOrig() {
		return orig;
	}
	public void setOrig(Vertex orig) {
		this.orig = orig;
	}
	public Vertex getDest() {
		return dest;
	}
	public void setDest(Vertex dest) {
		this.dest = dest;
	}
	public Edge(Vertex orig, Vertex dest) {
		super();
		this.orig = orig;
		this.dest = dest;
	}
	
}
