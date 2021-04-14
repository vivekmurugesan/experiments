package crack.code.chp4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import graphs.AdjList;
import graphs.Edge;
import graphs.Vertex;

public class BFSDFS {
	
	public static void bfs(AdjList graph, Vertex start) {
		
		Set<Vertex> visited = new HashSet<>();
		LinkedList<Vertex> que = new LinkedList<>();
		que.add(start);
		
		while(!que.isEmpty()) {
			Vertex u = que.removeFirst();
			visited.add(u);
			System.out.printf("%s\t", u.getId());
			
			List<Vertex> connected = graph.getConnectedVertices(u);
			for(Vertex v : connected) {
				if(!visited.contains(v)) {
					visited.add(v);
					que.add(v);
				}
			}
		}
	}
	
	public static void search(AdjList graph, Vertex start, Vertex end) {
		
		Set<Vertex> visited = new HashSet<>();
		LinkedList<Vertex> que = new LinkedList<>();
		que.add(start);
		
		while(!que.isEmpty()) {
			Vertex u = que.removeFirst();
			visited.add(u);
			System.out.printf("%s\t", u.getId());
			
			List<Vertex> connected = graph.getConnectedVertices(u);
			for(Vertex v : connected) {
				if(v.equals(end)) {
					System.out.println("\n found.. \n" + end.getId());
				}
				if(!visited.contains(v)) {
					visited.add(v);
					que.add(v);
				}
			}
		}
	}
	
	public static void dfs(AdjList graph, Vertex start, Set<Vertex> visited) {
		
		visited.add(start);
		System.out.printf("%s\t", start.getId());
		
		List<Vertex> connected = graph.getConnectedVertices(start);
		
		if(connected != null) {
			for(Vertex v : connected) {
				if(!visited.contains(v)) {
					dfs(graph, v, visited);
				}
			}
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
		
		AdjList graph = new AdjList(vertices, edgeList, false);
		
		System.out.println("bfs");
		bfs(graph, vertices.get(0));
		
		System.out.println("\ndfs");
		dfs(graph, vertices.get(0), new HashSet<>());
		
		System.out.println("\n.. Search..");
		search(graph, vertices.get(0), vertices.get(5));
	}
	
	

}
