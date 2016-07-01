import java.util.*;

public class SolverRPP{
<<<<<<< HEAD
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

=======
	
>>>>>>> 2eb89ec166c02eee4ea6bf785edd549a62db4a9f
}