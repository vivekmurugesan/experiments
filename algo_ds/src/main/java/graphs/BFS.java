package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author vivek
 *
 */
public class BFS {
	
	Map<Vertex, Vertex> parent = null;

	public void bfs(AdjList graph, Vertex start){
		parent = new HashMap<Vertex, Vertex>();
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		
		Map<Vertex, Boolean> discovered = new HashMap<Vertex, Boolean>();
		Map<Vertex, Boolean> processed = new HashMap<Vertex, Boolean>();
		discovered.put(start, true);
		queue.add(start);
		while(!queue.isEmpty()){
			Vertex x = queue.removeFirst();
			processVertex(x);
			processed.put(x, true);
			List<Vertex> edges = graph.getConnectedVertices(x);
			if(edges != null){
				for(Vertex v : edges){
					if(discovered.get(v) == null || !discovered.get(v)){
						queue.add(v);
						discovered.put(v, true);
						parent.put(v, x);
					}
				}
			}
		}
	}
	
	private void processVertex(Vertex v){
		System.out.printf("%s\t",v.getId());
	}
	
	
	
	public static void main(String[] args) {
		/** init vertex list */
		List<Vertex> vertices = new ArrayList();
		for(int i=1;i<=6;i++){
			vertices.add(new Vertex(String.valueOf(i)));
		}
		/** Init edge list 
		 *            1
		 *           / \
		 *         2 ----5
		 *       /  \     \
		 *      3    4     6
		 * */
		List<Edge> edgeList = new ArrayList();
		edgeList.add(new Edge(vertices.get(0), vertices.get(1)));
		edgeList.add(new Edge(vertices.get(0), vertices.get(4)));
		
		edgeList.add(new Edge(vertices.get(1), vertices.get(2)));
		edgeList.add(new Edge(vertices.get(1), vertices.get(3)));
		edgeList.add(new Edge(vertices.get(1), vertices.get(4)));
		
		edgeList.add(new Edge(vertices.get(4), vertices.get(5)));
		
		AdjList graph = new AdjList(vertices, edgeList);
		BFS bfs = new BFS();
		bfs.bfs(graph, vertices.get(0));
	}

}
