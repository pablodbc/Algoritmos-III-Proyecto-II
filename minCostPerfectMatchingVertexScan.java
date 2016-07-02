/**
 *	Vertex Scan
 *	Heuristic algorithm to find perfect matching of minimum cost in
 *  undirected complete graph <tt>G</tt> with even number of vertices
 *  proposed by David Avis.
 *
 *	@author Jose Acevedo
 *	@author Pablo Betancourt
 *
 */
import java.util.*;
public class minCostPerfectMatchingVertexScan {

	private HashSet<Edge> M;
	private boolean[][] vis;

	/**
	 *  Calculates minimum cost perfect matching in given graph <tt>G</tt>
	 *
	 *  @param G graph to be processed
	 *
	 */
	public minCostPerfectMatchingVertexScan(EdgeWeightedGraph G) {

		M = new HashSet<Edge>();
		boolean[] match = new boolean[G.V()];
		List<Integer> random = new ArrayList<Integer>();
		ArrayList<Edge>[] edges = new ArrayList[G.V()];
		vis = new boolean[G.V()][G.V()];

    	for (int v = 0; v < G.V(); v++) {
    		random.add(v);
    		match[v] = false;
    		edges[v] = new ArrayList();
    		for (Edge e : G.adj(v)) {
    			edges[v].add(e);
    		}
    		Collections.sort(edges[v]);
    		for (int u = 0; u < G.V(); u++)
    			vis[v][u] = false;
    	}

    	Collections.shuffle(random);

    	while (!random.isEmpty()) {
    		
    		int v = random.get(0);
    		random.remove(0);

    		if (match[v] || edges[v].size() <= 0) continue;
    		
    		Edge e = edges[v].get(edges[v].size() - 1);
    		edges[v].remove(edges[v].size() - 1);
			int u = e.other(v);
			while (edges[v].size() > 0 && vis[v][u] == true) {
    			e = edges[v].get(edges[v].size() - 1);
    			edges[v].remove(edges[v].size() - 1);
				u = e.other(v);				
			}

			match[v] = match[u] = true;
			M.add(e);

			for (Edge edge : G.adj(v)) {
				int w = edge.other(v);
				edges[w].remove(e);
			}
			for (Edge edge : G.adj(u)) {
				int w = edge.other(u);
				edges[w].remove(e);
			}
    	}
	}

	public HashSet<Edge> getEdges(){
		return M;
	}
}