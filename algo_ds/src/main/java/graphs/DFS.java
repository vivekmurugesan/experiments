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
public class DFS {
	
		public void dfs(AdjList graph, Vertex start){
		LinkedList<Vertex> stack = new LinkedList<Vertex>();
		
		Map<Vertex, Boolean> discovered = new HashMap<Vertex, Boolean>();
		Map<Vertex, Boolean> processed = new HashMap<Vertex, Boolean>();
		discovered.put(start, true);
		stack.add(start);
		while(!stack.isEmpty()){
			Vertex x = stack.getFirst();
			if(processed.get(x) == null || !processed.get(x))
				processVertex(x);
			processed.put(x, true);
			List<Vertex> edges = graph.getConnectedVertices(x);
			if(edges != null){
				boolean hasUndiscoveredChildren = false;
				for(Vertex v : edges){
					if(discovered.get(v) == null || !discovered.get(v)){
						stack.addFirst(v);
						discovered.put(v, true);
						hasUndiscoveredChildren = true;
						break;
					}
				}
				if(!hasUndiscoveredChildren)
					stack.removeFirst();
			}
		}
	}
	
	private void processVertex(Vertex v){
		System.out.printf("%s\t",v.getId());
	}
	
	public void dfs1(AdjList graph, Vertex start){
		
		Map<Vertex, Boolean> discovered = new HashMap<Vertex, Boolean>();
		Map<Vertex, Boolean> processed = new HashMap<Vertex, Boolean>();
		LinkedList<Vertex> stack = new LinkedList<Vertex>();
		
		discovered.put(start, true);
		stack.add(start);
		
		while(!stack.isEmpty()){
			Vertex v = stack.getFirst();
			List<Vertex> vertices = graph.getConnectedVertices(v);
			if(processed.get(v) == null || !processed.get(v)){
				processed.put(v, true);
				processVertex(v);
			}
			boolean hasUnVisitedChildren = false;
			if(vertices != null){
				for(Vertex x : vertices){
					if(discovered.get(x) == null || !discovered.get(x)){
						stack.addFirst(x);
						discovered.put(x, true);
						hasUnVisitedChildren = true;
					}
				}
			}
			if(!hasUnVisitedChildren)
				stack.removeFirst();
		}
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
		DFS dfs = new DFS();
		dfs.dfs1(graph, vertices.get(0));
	}


	
}
