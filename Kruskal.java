/**
 *	Kruskal's algorithm implementation to calculate minimum spanning tree of a graph <tt>G</tt> using disjoint-set data structure.
 *
 *	@author Jose Acevedo
 *	@author Pablo Betancourt
 *
 */
import java.util.*;

public class Kruskal {

	private double weight;
   	private Integer[] rep;
   	private Integer[] rank;
	private LinkedHashSet<Edge> MSTedges;

	/**
	 *  Returns set to which <tt>v</tt> belongs in disjoint-set data structure.
	 *
	 *	@param v vertex to be processed
	 *
	 *  @return set to which <tt>v</tt> belongs.
	 */
	private int find(int v) {
		if (rep[v] == v) 
			return v;
		else 
			return rep[v] = find(rep[v]);
	}

	/**
	 *  Merges sets containing vertices <tt>v</tt> and <tt>u</tt> respectively in disjoint-set data structure.
	 *
	 *	@param v vertex to merge
	 *	@param u vertex to merge
	 *
	 *  @return void
	 */
	private void merge(int v, int u) {

		v = find(v);
		u = find(u);

		if(rank[v] > rank[u])
			rep[u] = v;
		else if (rank[v] < rank[u])
			rep[v] = u;
		else {
			rep[u] = v;
			rank[v]++;
		}
	}

	/**
	 *  Calculates minimum spanning tree of given graph <tt>G</tt>
	 *
	 *  @param G graph to be processed
	 *
	 */
    public Kruskal(EdgeWeightedGraph G) {

    	weight = 0;
    	rep = new Integer[G.V()];
    	rank = new Integer[G.V()];
    	MSTedges = new LinkedHashSet<Edge>();
    	LinkedList<Edge> edges = new LinkedList<Edge>();

    	for (Edge e : G.edges())
    		edges.add(e);

    	for (int v = 0; v < G.V(); v++) {
    		rep[v] = v;
    		rank[v] = 0;
    	}

    	Collections.sort(edges);
    	for (Edge e : edges) {
    		int v = e.either();
    		int u = e.other(v);    		
    		if(find(v) != find(u)) {
    			weight += e.weight();
    			MSTedges.add(e);
    			merge(v,u);
    		}
    	}
    }

	/**
	 *  Returns set of edges that form the MST
	 *
	 *  @return edges set of edges of the minimum spanning tree
	 */
    public LinkedHashSet<Edge> getEdgesMST() {
		return MSTedges;
    }

	/**
	 *  Returns weight of the MST
	 *
	 *  @return weight sum of weights of the edges that form the MST
	 */
    public double weight() {
		return weight;
	}
}