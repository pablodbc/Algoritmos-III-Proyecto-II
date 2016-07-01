import java.util.*;

public class SolverRPP{
	private static Integer V;
	private static Integer aristasRequeridas;
	private static Integer aristasNoRequeridas;	
	private static EdgeWeightedGraph G;
	private static EdgeWeightedGraph Gr;

	public static void readFile(In in){
		in.readLine(); //Nombre
		in.readLine(); //Comentario
		V = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); //Numero de vertices
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
	public static void main(String[] args){
		In in = new In(args[0]);
		readFile(in);
	}
}