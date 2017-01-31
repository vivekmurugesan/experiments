package graphs.practice;

import graphs.AdjList;
import graphs.Edge;
import graphs.Vertex;

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

	/**
	 * Implementation of a recursive version.
	 * 1. Return if the vertex is null.
	 * 2. Mark the vertex as visited.
	 * 3. Get the adjacent vertices and iterate through them.
	 * 4. If the adjacent vertex is not visited already call dfs with the that vertex.
	 * 5. Before recursing on the vertex mark it as visited.
	 * @param graph
	 * @param start
	 */
	public void dfs(AdjList graph, Vertex start, Map<Vertex, Boolean> visited){
		if(start == null)
			return;
		process(start);
		List<Vertex> adjVertices = 
				graph.getConnectedVertices(start);
		visited.put(start, true);
		for(Vertex v : adjVertices){
			if(!visited.containsKey(v)){
				visited.put(v, true);
				dfs(graph, v, visited);
			}
		}
		
	}
	
	/**
	 * Implementation of an iterative version.
	 * 1. Keep couple of maps to track the visited and processed vertices.
	 * 2. Keep a stack to track visited and unprocessed vertices.
	 * 3. Mark the start vertex as visited and push it into the stack.
	 * 4. Pop out elements from the stack iterate until the stack is empty.
	 * 5. Get the adjacent vertices and iterate through them.
	 * 6. Check if the vertex is visited or not, if it is not push it into the stack.
	 * @param graph
	 * @param start
	 */
	public void dfsIter(AdjList graph, Vertex start){
		Map<Vertex, Boolean> visited = new HashMap<>();
		Map<Vertex, Boolean> processed = new HashMap<>();
		
		LinkedList<Vertex> stack = new LinkedList<>();
		stack.push(start);
		visited.put(start, true);
		
		while(!stack.isEmpty()){
			Vertex u = stack.pop();
			List<Vertex> adjVertices = graph.getConnectedVertices(u);
			for(Vertex v : adjVertices){
				if(!visited.containsKey(v)){
					stack.push(v);
					visited.put(v, true);
				}
			}
			processed.put(u, true);
			process(u);
		}
	}
	
	public void process(Vertex v){
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
		List<Edge> edgeList = new ArrayList<>();
		edgeList.add(new Edge(vertices.get(0), vertices.get(1)));
		edgeList.add(new Edge(vertices.get(0), vertices.get(4)));
		
		edgeList.add(new Edge(vertices.get(1), vertices.get(2)));
		edgeList.add(new Edge(vertices.get(1), vertices.get(3)));
		edgeList.add(new Edge(vertices.get(1), vertices.get(4)));
		
		edgeList.add(new Edge(vertices.get(4), vertices.get(5)));
		
		AdjList graph = new AdjList(vertices, edgeList, false);
		
		DFS dfs = new DFS();
		System.out.println(" Start pf dfs..");
		dfs.dfs(graph, vertices.get(0), new HashMap<Vertex, Boolean>());
		System.out.printf("\n Done \n");
		
		AdjList graph1 = new AdjList(vertices, edgeList, true);
		
		System.out.println(" Start pf dfs..");
		dfs.dfs(graph1, vertices.get(1), new HashMap<Vertex, Boolean>());
		System.out.printf("\n Done \n");
		
		System.out.println(".....Iterative version....");
		
		System.out.println(" Start pf dfs..");
		dfs.dfsIter(graph, vertices.get(0));
		System.out.printf("\n Done \n");
		
		System.out.println(" Start pf dfs..");
		dfs.dfsIter(graph1, vertices.get(1));
		System.out.printf("\n Done \n");
	}
}
