import java.util.*;

/**
 *	Clase principal encargada de probar dos algoritmos heuristicos
 *	con el fin de aproximar la solucion al problema del cartero rural 
 *	(Rural Postman Problem o RPP)
 *	
 *
 *	@author Jose Acevedo
 *	@author Pablo Betancourt
 *
 */
public class SolverRPP{
	private static long tiempoFinal, tiempoInicial;
	private static Integer V;
	private static Floyd floyd;
	private static Kruskal MST;
	private static Boolean flag;
	private static DepthFirstSearch DFS;
	private static EdgeWeightedGraph G,Gr,Gt;
	private static Integer aristasRequeridas;
	private static Integer aristasNoRequeridas;
	private static HashMap<Edge,ArrayList<Edge> > EtoP;


	/**
	 *	Procedimiento que lee y valida el archivo que contiene
	 *	la instancia RPP pasada por consola. Se hace uso de expresiones
	 *  regulares para limpiar la linea actual de caracteres no numericos y
	 *	se arman los grafos iniciales para empezar a resolver el problema.
	 *
	 *	@param In in
	 */
	public static void readFile(In in){
		in.readLine(); // Se ignora el nombre de la instancia
		in.readLine(); // Se ignora el comentario
		// Se lee el numero total de vertices
		V = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", ""));
		//Se crean los dos grafos G y Gr
		G = new EdgeWeightedGraph(V);
		Gr = new EdgeWeightedGraph(V);
		aristasRequeridas = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); //Aristas requeridas
		aristasNoRequeridas = Integer.valueOf(in.readLine().replaceAll("[^0-9]+", "")); // Aristas no requeridas
		if(V < 0) throw new IllegalArgumentException("El numero de vertices debe ser un numero positivo mayor o igual a cero.");
		if(aristasRequeridas < 0 || aristasNoRequeridas < 0) throw new IllegalArgumentException("El numero de aristas en ambos casos debe ser positivo.");
		in.readLine(); //Lista Aristas Req
		String[] lineaActual;
		Integer[] Aux = new Integer[2];

		// Se leen las aristas requeridas y se agregan al grafo G y al grafo Gr
		for(int i = 0; i<aristasRequeridas; i++){
			lineaActual = in.readLine().replaceAll("[^0-9]+", " ").trim().split(" ");
			if(lineaActual.length != 4)throw new IllegalArgumentException("Formato de archivo invalido.");
			for(int j = 0; j<2; j++) Aux[j] = Integer.valueOf(lineaActual[j]);
			Integer u = Aux[0] - 1,v = Aux[1] - 1;
			Double w = Double.valueOf(lineaActual[2]);
			Edge e = new Edge(u,v,w);
			G.addEdge(e);
			Gr.addEdge(e);

		}
		in.readLine(); //Aristas no requeridas
		// Se leen las aristas no requeridas y se agregan al grafo G
		for(int i = 0; i<aristasNoRequeridas; i++){
			lineaActual = in.readLine().replaceAll("[^0-9]+", " ").trim().split(" ");
			if(lineaActual.length != 4)throw new IllegalArgumentException("Formato de archivo invalido.");
			for(int j = 0; j<2; j++) Aux[j] = Integer.valueOf(lineaActual[j]);
			Integer u = Aux[0] - 1,v = Aux[1] - 1;
			Double w = Double.valueOf(lineaActual[2]);
			Edge e = new Edge(u,v,w);
			G.addEdge(e);
		}

	}

	/**
	 *	Procedimiento al cual se accede si el grafo inducido por las aristas
	 *	requeridas no es conexo. Se hace que el grafo Gr se convierta en un grafo
	 *	conexo a traves de 3 algoritmos: Un DFS para calcular las componentes conexas
	 *	del grafo, el algoritmo de Kruskal para calcular el arbol cobertor minimo
	 *	y el algoritmo de Floyd-Warshall para calcular caminos de costo minimo.
	 */

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

	/**
	 *	Procedimiento al cual se accede si el grafo es conexo pero no es par
	 *	Se hace uso del algoritmo de Floyd-Warshall para agregar aristas de costo
	 *	minimo asociadas a un camino de costo minimo en el grafo original G.
	 *	Luego se aplica alguno de los dos algoritmos de apareamiento perfecto
	 *	costo minimo (Depende del argumento pasado por consola) para agregar estas
	 *	nuevas aristas al grafo Gr.
	 */

	public static void lines16_22(){
		int N = 0;
		Integer[] oddVertex = new Integer[Gr.V()];
		for(int i = 0;i<Gr.V(); i++){
			if(Gr.degree(i)%2 != 0){
				oddVertex[N++] = i;
			}
		}
		EtoP = new HashMap<Edge,ArrayList<Edge> >();
		EdgeWeightedGraph Ga = new EdgeWeightedGraph(Gr.V());
		for(int i = 0; i<N; i++)
			for(int j = i+1; j<N; j++){
				Edge e = new Edge(oddVertex[i],oddVertex[j],floyd.dist(oddVertex[i],oddVertex[j]));
				EtoP.put(e,floyd.path(oddVertex[i],oddVertex[j]));
				Ga.addEdge(e);
			}
			if(flag){
				minCostPerfectMatchingGreedy MCPMG = new minCostPerfectMatchingGreedy(Ga);
				for(Edge e : MCPMG.getEdges()) Gr.addEdge(e);
			}else{
				minCostPerfectMatchingVertexScan MCPMV = new minCostPerfectMatchingVertexScan(Ga);
				for(Edge e : MCPMV.getEdges()) Gr.addEdge(e);
			}
		}

	/**
	 *	Procedimiento al cual se accede si el grafo es conexo y par. Ya en este punto
	 *	se calcula y se imprime el ciclo euleriano que debe contener el grafo Gr
	 */

	public static void lines23_24(){
		eulerianCycle eCycle = new eulerianCycle(Gr);
		Double peso = 0.0;
		if(eCycle.cycle() != null) {
			int v = eCycle.first().either();
			for(Edge i : eCycle.cycle()){
				peso += i.weight();
				StdOut.print((v+1) + " ");
				v = i.other(v);
			}
		}
		StdOut.println("1");
		StdOut.println(peso);
		tiempoFinal = System.nanoTime();
		StdOut.printf("%.3f segs.\n", ((tiempoFinal - tiempoInicial)/1e9));
	}

	public static void main(String[] args){
		tiempoInicial = System.nanoTime();
		if(args.length !=2){
			throw new IllegalArgumentException("Formato invalido, Debe correr el programa con >java SolverRPP [-g] [-s] <instancia> , donde [-g] y [-s] representan la heuristica que se desea utilizar e <instancia> es la direccion del archivo con la instancia de RPP a resolver.");
		}
		if(args[0].equals("-g")){
			flag = true;
		}else if (args[0].equals("-s")){
			flag = false;
		}else{
			throw new IllegalArgumentException("Formato invalido, Debe correr el programa con >java SolverRPP [-g] [-s] <instancia> , donde [-g] y [-s] representan la heuristica que se desea utilizar e <instancia> es la direccion del archivo con la instancia de RPP a resolver.");
		}
		In in = new In(args[1]);
		readFile(in);
		DFS = new DepthFirstSearch(Gr);
		floyd = new Floyd(G);
		if(DFS.esConexo()){
			if(DFS.esPar()){
				lines23_24();
			}else{
				lines16_22();
				lines23_24();
			}
		}else{
			lines9_15();
			lines16_22();
			lines23_24();
		}

	}
}