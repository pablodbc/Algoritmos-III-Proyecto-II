/**
 *	Depth first search algorithm to traverse given graph <tt>G</tt>
 *
 *	@author Jose Acevedo
 *	@author Pablo Betancourt
 *
 */
import java.util.*;
public class DepthFirstSearch{

	private Boolean[] Visited;
	private EdgeWeightedGraph G;
	private static ArrayList<HashSet<Integer> > compCon;

	/**
	 *	DFS traverse algorithm in weighted undirected graph
	 *
	 *  @param G weighted undirected graph to be traversed
	 *
	 */
	public DepthFirstSearch(EdgeWeightedGraph G){
		this.G = G;
		Visited = new Boolean[G.V()];
		compCon = new ArrayList<HashSet<Integer> >();
		for(int i = 0; i<G.V(); i++) Visited[i] = false;
		for(int i = 0; i<G.V(); i++){
			if(!Visited[i]) {
				HashSet S = new HashSet();
				compCon.add(S);
				DFS(i);
			}
		}
	}

	/**
	 *	DFS algorithm
	 *
	 *  @param v vertex in traversal
	 */	
	public void DFS(int v){
		Visited[v] = true;
		compCon.get(compCon.size() -1).add(v);
		for(Edge i : G.adj(v)){
			if(!Visited[i.other(v)]) DFS(i.other(v));
		}

	}

	/**
	 *	Returns true if graph is connected, false otherwise.
	 *
	 *  @return true if graph is connected
	 */
	public boolean esConexo(){
		return compCon.size() == 1;
	}

	/**
	 *	Returns true if graph is even, false otherwise.
	 *
	 *  @return true if graph is even
	 */
	public boolean esPar(){
		for(int i = 0; i<G.V(); i++){
			if(G.degree(i)%2 != 0) return false;
		}
		return true;
	}

	/**
	 *	Returns connected components of the graph in array list
	 *
	 *  @return connected components in ArrayList
	 */
	public ArrayList<HashSet<Integer> > compCon(){
		return compCon;
	}
}