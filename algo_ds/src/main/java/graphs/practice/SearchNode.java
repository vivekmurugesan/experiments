package graphs.practice;

import graphs.AdjList;
import graphs.BFS;
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
public class SearchNode {

	/**
	 * 1. Start the traversal from vertex start.
	 * 2. Add all the adjacent nodes to the queue.
	 * 3. While adding nodes into the queue mark them as visited.
	 * 4. Process vertices from the queue one by one.
	 * 5. While processing check if the vertex is the desired end vertex.
	 * 6. If it is return true.
	 * 7. Continue the traversal otherwise, if there are no more nodes to
	 * visit/process return false.
	 * @param graph
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean search(AdjList graph, Vertex start, Vertex end){
		// TODO null checks of params.
		if(start.equals(end))
			return true;
		
		// TODO: to introduce flags into the vertex node to do this.
		Map<Vertex, Boolean> visited = new HashMap<>();
		Map<Vertex, Boolean> processed = new HashMap<>();
		LinkedList<Vertex> que = new LinkedList<>();
		visited.put(start, true);processed.put(start, true);
		que.add(start);
				
		boolean found = false;
		while(!que.isEmpty()){
			Vertex v = que.removeFirst();
			if(v.equals(end)){
				found = true;
				break;
			}
			List<Vertex> adjVertices = 
					graph.getConnectedVertices(v);
			for(Vertex adjV : adjVertices){
				if(adjV.equals(end)){
					found=true;
					break;
				}
				if(!visited.containsKey(adjV) &&
						!processed.containsKey(adjV)){
					que.add(adjV);
					visited.put(adjV, true);
				}
			}
			if(found)break;
			
			processed.put(v, true);
		}
		
		return found;
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
		/*BFS bfs = new BFS();
		bfs.bfs(graph, vertices.get(0));*/
		SearchNode searcher = new SearchNode();
		// True in the case bidirectional, false in unidirectional
		if(searcher.search(graph, vertices.get(2), vertices.get(5)))
			System.out.println("End vertex found");
		else
			System.out.println("End vertex not found..");
		
		if(searcher.search(graph, vertices.get(0), new Vertex("100")))
			System.out.println("End vertex found");
		else
			System.out.println("End vertex not found..");
	}
}
