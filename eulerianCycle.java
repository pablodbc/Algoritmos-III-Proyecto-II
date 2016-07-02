/**
 *	Algorithm to calculate an eulerian cycle in a given connected graph <tt>G</tt>
 *	with even degree vertices
 *
 *	@author Jose Acevedo
 *	@author Pablo Betancourt
 *
 */
import java.util.*;

public class eulerianCycle{

	private Integer[] count;
	private ArrayList<Edge>[] Gp;
	private EdgeWeightedGraph G;
	private ArrayList<Edge> cycle;
	private boolean[][] vis;

	/**
	 *  Calculates eulerian cycle in given graph
	 *
	 *  @param G graph to be processed
	 *
	 */
	public eulerianCycle(EdgeWeightedGraph G){

		count = new Integer[G.V()];
		cycle = new ArrayList<Edge>();
		Gp = new ArrayList[G.V()];
		vis = new boolean[G.V()][G.V()];

		for (int v = 0; v < G.V(); v++) {
    		Gp[v] = new ArrayList();
			for (Edge e : G.adj(v)) {
				Gp[v].add(e);
			}   
			for (int u = 0; u < G.V(); u++)
				vis[v][u]=false;
		}
		DFS(0);
	}

	/**
	 *	DFS traversal algorithm to calculate eulerian cycle
	 *
	 *  @param v vertex to be processed
	 *
	 */
	private void DFS(int v) {
		while(!Gp[v].isEmpty() && vis[v][Gp[v].get(0).other(v)]) Gp[v].remove(0);
		if (!Gp[v].isEmpty()) {
			Edge e = Gp[v].get(0);
			Gp[v].remove(0);
			cycle.add(e);
			vis[v][e.other(v)] = vis[e.other(v)][v] = true;
			DFS(e.other(v));
		}
	}

	/**
	 *	Returns eulerian cycle
	 */
	public ArrayList<Edge> cycle() {
		return cycle;
	}
}