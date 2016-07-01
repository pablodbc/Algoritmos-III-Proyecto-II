import java.util.*;

public class SolverRPP{
	private static Integer V;
	private static Integer aristasRequeridas;
	private static Integer aristasNoRequeridas;	
	private static EdgeWeightedDigraph G;
	private static EdgeWeightedDigraph Gr;

	public static void readFile(In in){
		in.readLine(); //Nombre
		in.readLine(); //Comentario
		V = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); //Numero de vertices
		aristasRequeridas = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); //Aristas requeridas
		aristasNoRequeridas = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); // Aristas no requeridas
		in.readLine(); //Lista Aristas Req
		String[] lineaActual;
		Integer[] Aux = new Integer[4];

		for(int i = 0; i<aristasRequeridas; i++){
			lineaActual = in.readLine().replaceAll("[^0-9]+", " ").trim().split(" ");
			for(int j = 0; j<4; j++) Aux[j] = Integer.valueOf(lineaActual[j]);
		}

		
		for(int i = 0; i<aristasRequeridas; i++){
			lineaActual = in.readLine().replaceAll("[^0-9]+", " ").trim().split(" ");
			for(int j = 0; j<4; j++) Aux[j] = Integer.valueOf(lineaActual[j]);
		}

	}
	public static void main(String[] args){
		In in = new In(args[0]);
		readFile(in);
	}
}