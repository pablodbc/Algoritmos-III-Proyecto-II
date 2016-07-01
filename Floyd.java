/**
 *	Floyd-Warshall's algorithm implementation to calculate shortest path between all pairs of vertices.
 *
 *	@author Jose Acevedo
 *	@author Pablo Betancourt
 *
 */
import java.util.*;

public class Floyd {

	private double[][] D;
	private double[][] W;
	private Integer[][] next;

	/**
	 *  Calculates shortest path between all pairs of vertices of given graph <tt>G</tt>
	 *
	 *  @param G directed graph to be processed
	 *
	 */
     public Floyd(EdgeWeightedGraph G) {

     	D = new double[G.V()][G.V()];
     	W = new double[G.V()][G.V()];
     	next = new Integer[G.V()][G.V()];

     	for (int i = 0; i < G.V(); i++) {
     		for (int j = 0; j < G.V(); j++) {
     			D[i][j] = Double.MAX_VALUE;
     			W[i][j] = Double.MAX_VALUE;
     		}
     		D[i][i] = 0;
     		W[i][i] = 0;
     	}

     	for (int v = 0; v < G.V(); v++) {
     		for (Edge e : G.adj(v)) {
     			D[v][e.other(v)] = e.weight();
     			W[v][e.other(v)] = e.weight();
     			next[v][e.other(v)] = e.other(v);
     		}
     	}

     	for (int k = 0; k < G.V(); k++) {
     		for (int i = 0; i < G.V(); i++) {
     			for (int j = 0; j < G.V(); j++) {
     				if (D[i][k] + D[k][j] < D[i][j]) {
     					D[i][j] = D[i][k] + D[k][j];
     					next[i][j] = next[i][k];
     				}
     			}
     		}
     	}
     }

	/**
	 *  Returns weight of shortest path from vertex <tt>s</tt> to vertex <tt>t</tt>.
	 *
	 *  @param s source vertex of the path
	 *  @param t end vertex of the path
	 *
	 *  @return weight of shortest path from <tt>s</tt> to <tt>t</tt>
	 */
     public double dist(int s, int t) {
	 	return D[s][t];
     }

	/**
	 *  Returns directed edges of shortest path from vertex <tt>s</tt> to vertex <tt>t</tt>.
	 *
	 *  @param s source vertex of the path
	 *  @param t end vertex of the path
	 *
	 *  @return set of edges of shortest path from <tt>s</tt> to <tt>t</tt> as a list
	 */
     public ArrayList<Edge> path(int s, int t) {
	  
	 	ArrayList<Edge> path = new ArrayList<Edge>();
		if(next[s][t] == null) return null;
		while(s != t) {
	 		path.add(new Edge(s, next[s][t], W[s][next[s][t]]));
	 		s = next[s][t];
	 	}
		return path; 
     }
}
