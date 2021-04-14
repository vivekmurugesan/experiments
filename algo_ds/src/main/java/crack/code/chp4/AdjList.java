package crack.code.chp4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author vivek
 *
 */
public class AdjList {
	private Map<Vertex, List<Vertex>> adjList = null;
	private List<Vertex> vertices;
	private List<Edge> edges;
	
	private boolean biDirectional = true;
	
	public AdjList(List<Vertex> vertices, List<Edge> edges) {
		this(vertices, edges, true);
	}
	
	public AdjList(List<Vertex> vertices, List<Edge> edges, boolean biDirectional) {
		this.vertices = vertices;
		this.edges = edges;
		this.biDirectional = biDirectional;
		init();
	}
	
	public void init(){
		if(vertices != null){
			adjList = new HashMap<Vertex, List<Vertex>>();
			if(edges != null){
				for(Edge edge : edges){
					addEdgeMapping(edge);
				}
			}
		}
	}
	
	private void addEdgeMapping(Edge edge){
		if(edge.getOrig() != null && edge.getDest() != null ){
			Vertex v = edge.getOrig();
			List<Vertex> vList = adjList.get(v);
			if(vList == null){
				vList = new LinkedList<Vertex>();
				adjList.put(v, vList);
			}
			vList.add(edge.getDest());
			
			if(biDirectional){
				v = edge.getDest();
				vList = adjList.get(v);
				if(vList == null){
					vList = new LinkedList<Vertex>();
					adjList.put(v, vList);
				}
				vList.add(edge.getOrig());
			}
		}
	}
	
	public List<Vertex>  getConnectedVertices(Vertex v){
		List<Vertex> result = adjList.get(v);
		if(result == null)
			result = new LinkedList<>();
		return result;
	}
}
