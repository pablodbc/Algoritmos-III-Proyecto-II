import java.util.*;

public class DepthFirstSearch{
	private Boolean[] Visited;
	private EdgeWeightedGraph G;
	private static ArrayList<HashSet<Integer> > compCon;

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
	public void DFS(int v){
		Visited[v] = true;
		compCon.get(compCon.size() -1).add(v);
		for(Edge i : G.adj(v)){
			if(!Visited[i.other(v)]) DFS(i.other(v));
		}

	}

	public boolean esConexo(){
		return compCon.size() == 1;
	}

	public boolean esPar(){
		for(int i = 0; i<G.V(); i++){
			if(G.degree(i)%2 != 0) return false;
		}
		return true;
	}
	public ArrayList<HashSet<Integer> > compCon(){
		return compCon;
	}
}