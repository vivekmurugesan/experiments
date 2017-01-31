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
public class BFS {
	
	/**
	 * 1. Have a queue to track all the visited vertices and to be processed.
	 * 2. Have maps visited and processed to track all the visited and processed vertices.
	 * 3. Add the start vertex into the queue.
	 * 4. For each vertex in the queue, fetch the adjacent vertices.
	 * 5. For each of the adjacent vertex, check if it has been visited already or not.
	 * 6. If it is not visited already, enqueue it for processing.
	 * 6.a Whenever an unvisited node is discovered, add it to the distances 
	 * map by incrementing its distance to be dist(parent)+1 
	 * 7. Process the current node in process.
	 * 8. Iterate through until the queue is empty.
	 * 
	 * At the end of this process we will have the shortest path distance for
	 * all nodes from the root would be computed.
	 * @param graph
	 * @param start
	 */
	public Map<Vertex, Integer> bfsShortestPath(AdjList graph, Vertex start){
		
		Map<Vertex, Boolean> visited = new HashMap<>();
		Map<Vertex, Boolean> processed = new HashMap<>();
		LinkedList<Vertex> queue = new LinkedList<>();
		Map<Vertex, Integer> distances = new HashMap<>();
		
		queue.add(start);
		distances.put(start, 0);
		System.out.println("Start of bfs..");
		
		while(!queue.isEmpty()){
			Vertex u = queue.removeFirst();
			visited.put(u, true);
			
			List<Vertex> adjVertices = graph.getConnectedVertices(u);
			for(Vertex v : adjVertices){
				if(!visited.containsKey(v) && !processed.containsKey(v)){
					queue.add(v);
					visited.put(v, true);
					distances.put(v, distances.get(u)+1);
				}
			}
			
			process(u);
			processed.put(u, true);
		}
		
		System.out.printf("\n done..\n");
		
		return distances;
	}
	
	
	/**
	 * 1. Have a queue to track all the visited vertices and to be processed.
	 * 2. Have maps visited and processed to track all the visited and processed vertices.
	 * 3. Add the start vertex into the queue.
	 * 4. For each vertex in the queue, fetch the adjacent vertices.
	 * 5. For each of the adjacent vertex, check if it has been visited already or not.
	 * 6. If it is not visited already, enqueue it for processing.
	 * 7. Process the current node in process.
	 * 8. Iterate through until the queue is empty.
	 * @param graph
	 * @param start
	 */
	public void bfs(AdjList graph, Vertex start){
		
		Map<Vertex, Boolean> visited = new HashMap<>();
		Map<Vertex, Boolean> processed = new HashMap<>();
		LinkedList<Vertex> queue = new LinkedList<>();
		
		queue.add(start);
		System.out.println("Start of bfs..");
		
		while(!queue.isEmpty()){
			Vertex u = queue.removeFirst();
			visited.put(u, true);
			
			List<Vertex> adjVertices = graph.getConnectedVertices(u);
			for(Vertex v : adjVertices){
				if(!visited.containsKey(v) && !processed.containsKey(v)){
					queue.add(v);
					visited.put(v, true);
				}
			}
			
			process(u);
			processed.put(u, true);
		}
		
		System.out.printf("\n done..\n");
		
	}
	
	private void process(Vertex v){
		System.out.printf("%s\t", v.getId());
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
		
		BFS bfs = new BFS();
		bfs.bfs(graph, vertices.get(1));
		
		AdjList graph1 = new AdjList(vertices, edgeList, true);
		
		bfs.bfs(graph1, vertices.get(1));
		
		Map<Vertex, Integer> distances =
				bfs.bfsShortestPath(graph1, vertices.get(0));
		System.out.println("Shortest path:" + distances);
	}
}
