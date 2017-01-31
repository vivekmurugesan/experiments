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
public class BFSAndDFS {
	
	Map<Vertex,Vertex> parent = null;
	public void bfs(AdjList graph, Vertex start){
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		
		Map<Vertex,Boolean> discovered = new HashMap<Vertex,Boolean>();
		Map<Vertex,Boolean> processed = new HashMap<Vertex,Boolean>();
		parent = new HashMap<Vertex,Vertex>();
		queue.add(start);
		discovered.put(start, true);
		
		while(!queue.isEmpty()){
			Vertex v = queue.removeFirst();
			processVertexEarly(v);
			processed.put(v, true);
			List<Vertex> connVertices = graph.getConnectedVertices(v);
			if(connVertices != null){
				for(Vertex y : connVertices){
					if(discovered.get(y) == null || !discovered.get(y)){
						queue.addLast(y);
						discovered.put(y, true);
						parent.put(y,v);
					}
				}
			}
		}
	}
	
	public void dfs1(AdjList graph, Vertex start){
		LinkedList<Vertex> stack = new LinkedList<Vertex>();
		
		Map<Vertex, Boolean> discovered = new HashMap<Vertex, Boolean>();
		Map<Vertex, Boolean> processed = new HashMap<Vertex, Boolean>();
		
		stack.addFirst(start);
		discovered.put(start, true);
		boolean hasUndiscoveredChild = false;
		
		while(!stack.isEmpty()){
			Vertex v = stack.getFirst();
			if(processed.get(v) == null || !processed.get(v))
				processVertexEarly(v);
			processed.put(v, true);
			List<Vertex> connVertices = graph.getConnectedVertices(v);
			if(connVertices != null && !connVertices.isEmpty()){
				hasUndiscoveredChild = false;
				for(Vertex y : connVertices){
					if(discovered.get(y) == null || !discovered.get(y)){
						stack.addFirst(y);
						discovered.put(y, true);
						hasUndiscoveredChild = true;
						break;
					}
				}
				if(!hasUndiscoveredChild)
					stack.removeFirst();
			}/** In bi-directional graph we will never encounter this. */
			/*else{
				Vertex z =stack.removeFirst();
				System.out.println("removed:" + z.getId());
				break;
			}*/
		}
	}
	
	Map<Vertex, Boolean> marked = new HashMap<Vertex, Boolean>();
	int count = 0;
	
	public void reset(){
		 marked = new HashMap<Vertex, Boolean>();
	}
	
	public void dfs(AdjList graph, Vertex start){
		processVertexEarly(start);
		marked.put(start,true);
		count++;
		List<Vertex> connVertices = graph.getConnectedVertices(start);
		if(connVertices != null){
			for(Vertex v:connVertices){
				if(marked.get(v) == null || !marked.get(v))
					dfs(graph, v);
			}
		}
	}
	
	public void topoSort(AdjList graph, Vertex start, LinkedList<Vertex> stack){
		processVertexEarly(start);
		marked.put(start,true);
		count++;
		List<Vertex> connVertices = graph.getConnectedVertices(start);
		if(connVertices != null){
			for(Vertex v:connVertices){
				if(marked.get(v) == null || !marked.get(v))
					topoSort(graph, v, stack);
			}
		}
		stack.push(start);

	}
	
	Map<Vertex, Boolean> discovered = new HashMap<Vertex, Boolean>();
	Map<Vertex, Boolean> processed = new HashMap<Vertex, Boolean>();
	
	public void bfsRecur(AdjList graph, Vertex start){
		if(discovered.get(start) == null || !discovered.get(start)){
			processVertexEarly(start);
			discovered.put(start, true);
		}
		List<Vertex> connVertices = graph.getConnectedVertices(start);
		if(connVertices != null){
			for(Vertex v:connVertices){
				if(discovered.get(v) == null || !discovered.get(v)){
					discovered.put(v, true);
					processVertexEarly(v);
				}
			}
			for(Vertex v:connVertices){
				if(processed.get(v) == null || !processed.get(v)){
					processed.put(v, true);
					bfsRecur(graph,v);
				}
			}
		}
	}
	
	private void processVertexEarly(Vertex v){
		System.out.print(" --> " + v.getId());
	}
	
	public List<Vertex> path(Vertex x, Vertex y){
		if(parent == null)
			return null;
		List<Vertex> path = new ArrayList<Vertex>();
		Vertex parentV = y;
		path.add(parentV);
		do{
			parentV = parent.get(parentV);
			path.add(parentV);
		}while(!parentV.equals(x));
		
		return path;
	}
	
	public void printPath(List<Vertex> path){
		if(path != null){
			Vertex[] pathArr = path.toArray(new Vertex[0]);
			for(int i=pathArr.length-1;i>=0;i--){
				System.out.print(pathArr[i].getId()+",");
			}
		}
	}
	
	public static void main(String[] args){
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
		
		BFSAndDFS bfsDfs = new BFSAndDFS();
		System.out.println(".. BFS...");
		bfsDfs.bfs(graph, vertices.get(0));
		System.out.println();
		System.out.println(".. BFS Recur...");
		bfsDfs.bfsRecur(graph, vertices.get(0));
		System.out.println();
		bfsDfs.reset();
		System.out.println("... DFS ...");
		bfsDfs.dfs(graph, vertices.get(0));
		System.out.println();
		
		bfsDfs.reset();
		System.out.println("... Topo sort ...");
		Map<Vertex, Integer> f = new HashMap<>();
		LinkedList<Vertex> stack = new LinkedList<>();
		bfsDfs.topoSort(graph, vertices.get(0), stack);
		for(int i=1;!stack.isEmpty();i++){
			Vertex v = stack.pop();
			f.put(v, i);
		}
		System.out.println();
		System.out.println("Topo function:" + f);
		System.out.println();
		
		System.out.println("... DFS1 ...");
		bfsDfs.dfs1(graph, vertices.get(0));
		System.out.println();
		
		/** Testing path */
		/*System.out.println("Path between 1 to 6" + bfsDfs.path(vertices.get(0), vertices.get(5)));*/
		bfsDfs.printPath(bfsDfs.path(vertices.get(0), vertices.get(5)));
		//bfsDfs.printPath(bfsDfs.path(vertices.get(3), vertices.get(5)));
		System.out.printf("\n%d %f", 20, 20.7);
	}
}
