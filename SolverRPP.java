import java.util.*;

public class SolverRPP{
	private static Integer V;
	private static Floyd floyd;
	private static DepthFirstSearch DFS;
	private static EdgeWeightedGraph G,Gr,Gt;
	private static Integer aristasRequeridas;
	private static Integer aristasNoRequeridas;
	private static HashMap<Edge,ArrayList<Edge> > EtoP;
	private static LinkedHashSet<Integer> vertexInGraphR;


	public static void readFile(In in){
		vertexInGraphR = new LinkedHashSet<Integer>();
		in.readLine(); //Nombre
		in.readLine(); //Comentario
		V = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); //Numero de vertices
		G = new EdgeWeightedGraph(V);
		Gr = new EdgeWeightedGraph(V);
		aristasRequeridas = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); //Aristas requeridas
		aristasNoRequeridas = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); // Aristas no requeridas
		in.readLine(); //Lista Aristas Req
		String[] lineaActual;
		Integer[] Aux = new Integer[2];

		// Se leen las aristas requeridas y se agregan al grafo G y al grafo Gr
		for(int i = 0; i<aristasRequeridas; i++){
			lineaActual = in.readLine().replaceAll("[^0-9]+", " ").trim().split(" ");
			for(int j = 0; j<2; j++) Aux[j] = Integer.valueOf(lineaActual[j]);
			Integer u = Aux[0] - 1,v = Aux[1] - 1;
			Double w = Double.valueOf(lineaActual[2]);
			Edge e = new Edge(u,v,w);
			G.addEdge(e);
			Gr.addEdge(e);
			vertexInGraphR.add(u);
			vertexInGraphR.add(v);

		}
		in.readLine(); //Aristas no requeridas
		// Se leen las aristas no requeridas y se agregan al grafo G
		for(int i = 0; i<aristasNoRequeridas; i++){
			lineaActual = in.readLine().replaceAll("[^0-9]+", " ").trim().split(" ");
			for(int j = 0; j<2; j++) Aux[j] = Integer.valueOf(lineaActual[j]);
			Integer u = Aux[0] - 1,v = Aux[1] - 1;
			Double w = Double.valueOf(lineaActual[2]);
			Edge e = new Edge(u,v,w);
			G.addEdge(e);
		}

	}

	public static void lines9_15(){
		Double minCost;
		Integer either = 0;
		EtoP = new HashMap<Edge,ArrayList<Edge> >();
		ArrayList<Edge> path = new ArrayList<Edge>();
		EdgeWeightedGraph Gt = new EdgeWeightedGraph(DFS.compCon().size());

		for(HashSet<Integer> Compi : DFS.compCon()){
			Integer other = 0;
			for(HashSet<Integer> Compj : DFS.compCon()){
				if(either <= other) {other++;continue;}
				minCost = Double.MAX_VALUE;
				for(Integer i : Compi){
					for(Integer j : Compj){
						if(minCost > floyd.dist(i,j)){
							minCost = floyd.dist(i,j);
							path = floyd.path(i,j);
						}
					}
				}

				Edge e = new Edge(either,other,minCost);
				EtoP.put(e,path);
				Gt.addEdge(e);
				other++;
			}
			either++;
		}
		Kruskal MST = new Kruskal(Gt);
		HashSet<Edge> H = new HashSet<Edge>();
		for(Edge e : MST.getEdgesMST()){
			ArrayList<Edge> Edges = EtoP.get(e);
			for(Edge i : Edges) H.add(i);
		}
		for(Edge e : H) Gr.addEdge(e);
	}

	public static void lines16_22(){
		Integer[] oddVertex = new Integer[Gr.V()];
		int N = 0;
		for(int i = 0;i<Gr.V(); i++){
			if(Gr.degree(i)%2 != 0){
				oddVertex[N++] = i;
			}
		}
		EdgeWeightedGraph Ga = new EdgeWeightedGraph(Gr.V());
		EtoP = new HashMap<Edge,ArrayList<Edge> >();
		for(int i = 0; i<N; i++)
			for(int j = i+1; j<N; j++){
				Edge e = new Edge(oddVertex[i],oddVertex[j],floyd.dist(oddVertex[i],oddVertex[j]));
				EtoP.put(e,floyd.path(oddVertex[i],oddVertex[j]));
				Ga.addEdge(e);
			}
			minCostPerfectMatchingGreedy MCPMG = new minCostPerfectMatchingGreedy(Ga);
			for(Edge e : MCPMG.getEdges()) Gr.addEdge(e);
	}

	public static void main(String[] args){
		In in = new In(args[0]);
		readFile(in);
		DFS = new DepthFirstSearch(Gr);
		floyd = new Floyd(G);
		lines9_15();
		lines16_22();
	}
}