package graphs;

/**
 * 
 * @author vivek
 *
 */
public class Vertex {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Vertex(String id) {
		super();
		this.id = id;
	}
	
	public String toString(){
		return id;
	}
}
