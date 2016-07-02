/**
 *	Greedy heuristic algorithm to find perfect matching of minimum cost in
 *  undirected complete graph <tt>G</tt> with even number of vertices.
 *
 *	@author Jose Acevedo
 *	@author Pablo Betancourt
 *
 */
import java.util.*;
public class minCostPerfectMatchingGreedy {

	private HashSet<Edge> M;

	/**
	 *  Calculates minimum cost perfect matching in <tt>G</tt> with greedy strategy
	 *
	 *  @param G graph to be processed
	 *
	 */
	public minCostPerfectMatchingGreedy(EdgeWeightedGraph G) {

		Integer count = G.V();
		M = new HashSet<Edge>();
		boolean[] match = new boolean[G.V()];
		TreeSet<Edge> edges = new TreeSet<Edge>();
	
    	for (int v = 0; v < G.V(); v++)
    		match[v] = false;
	
		for (Edge e : G.edges())
			edges.add(e);

    	for (Edge e : edges) {
    		int v = e.either();
    		int u = e.other(v);
    		if (!match[v] && !match[u]) {
    			M.add(e);
    			match[v] = match[u] = true;
    			count -= 2;
    			if (count <= 0) 
    				break;
    		}
    	}
	}

	/**
	 *	Returns set of edges of the perfect matching
	 */
	public HashSet<Edge> getEdges(){
		return M;
	}
}