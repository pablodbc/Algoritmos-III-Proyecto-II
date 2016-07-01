import java.util.*;

public class SolverRPP{
	private static Integer V;
	private static Integer req;
	private static Integer no_req;	
	private static EdgeWeightedDigraph G;

	public static void readFile(In in){
		in.readLine(); //Nombre
		in.readLine(); //Comentario
		V = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); //Numero de vertices
		req = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); //Aristas requeridas
		no_req = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); // Aristas no requeridas
		System.out.printf("%d %d %d\n",V,req,no_req);


	}
	public static void main(String[] args){
		In in = new In(args[0]);
		readFile(in);
	}
}