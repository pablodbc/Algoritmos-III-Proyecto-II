import java.util.*;

public class SolverRPP{
	private static Integer V;
	private static Kruskal MST;
	private static DepthFirstSearch DFS;
	private static EdgeWeightedGraph G,Gr,Gt;
	private static Integer aristasRequeridas;
	private static Integer aristasNoRequeridas;
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

	public static EdgeWeightedGraph armarGCC(EdgeWeightedGraph G){
		Floyd floyd = new Floyd(G);
		EdgeWeightedGraph GCC = new EdgeWeightedGraph(DFS.compCon().size());
		Double minCost;
		Integer either = 0;
		for(HashSet<Integer> Compi : DFS.compCon()){
			Integer other = 0;
			for(HashSet<Integer> Compj : DFS.compCon()){
				if(either <= other) {other++;continue;}
				minCost = Double.MAX_VALUE;
				for(Integer i : Compi){
					for(Integer j : Compj){
						if(minCost > floyd.dist(i,j)){
							minCost = floyd.dist(i,j);
						}
					}
				}

				Edge e = new Edge(either,other,minCost);
				GCC.addEdge(e);
				other++;
			}
			either++;
		}
		return GCC;
	}



	public static void main(String[] args){
		In in = new In(args[0]);
		readFile(in);
		DFS = new DepthFirstSearch(Gr);
		if(DFS.esConexo()){
			if(DFS.esPar()){

			}else{

			}
		}
		EdgeWeightedGraph Gt = armarGCC(Gr);
		MST = new Kruskal(Gt);


	}
}