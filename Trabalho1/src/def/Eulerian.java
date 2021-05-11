package def;

import java.io.*;

public class Eulerian {

	public static String filename;
	public static void main(String[] args) {
		if(args.length !=1) {
			System.out.println("Só é possivel entrar com o caminho do arquivo");
			System.exit(-1) ;
		}
		else {
			filename = args[0];
		}

		Graph my_graph;
		boolean eulerian_graph;

		try {
			// Cria uma instancia de arquivo
			File file = new File(filename);
			// Le o arquivo de entrada
			FileReader fr = new FileReader(file);
			// Cria uma instancia do leitor para o arquivo
			BufferedReader br = new BufferedReader(fr);
			// Cria uma string para iterar pelas linhas do arquivo
			String line;

			// Conta as linhas do arquivo
			try {
				BufferedReader count = new BufferedReader(new FileReader(filename));
				int max = 0;
				while((line = count.readLine()) != null) {
					String[] lineList = line.split("=");
					int v = Integer.parseInt( (lineList[0].replaceAll(" ", "")) );
					if(max < v) max = v;
				}
				my_graph = new Graph(max);

				// Le cada linha da entrada e adiciona cada vertice reconhecido ao grafo
				// A entrada eh divida em duas partes utilizando o '=' como separador
				// Cada vertice apos o '=' eh conectado entao ao vertice antes do '='
				while((line = br.readLine()) != null) {
					String[] lineList= line.split("=");
					String[] adjList;

					if(lineList.length==0) {
						System.out.println("Erro na entrada");
						System.exit(-1);
					}
					int v = Integer.parseInt((lineList[0].replaceAll(" ", "")));

					if(lineList.length == 2 && !lineList[1].equals(" ")) {

						adjList = lineList[1].split(" ");
						for (String wstr : adjList) {
							if (!wstr.isEmpty()) {
								int w = Integer.parseInt(wstr);
								my_graph.addEdge(v - 1, w - 1);
							}
						}
					}

					else if (lineList.length == 1 || lineList[1].equals(" ")) {
							my_graph.addEdge(v - 1, v - 1);
						}
					}


				// Finaliza o processo de leitura
				br.close();

				// Roda o teste programado no arquivo "Grafo.java"
				// e define se o grafo eh ou nao euleriano
				eulerian_graph = my_graph.getClassification();

				// Printa o caminho/circuito euleriano caso possivel
				if(eulerian_graph) {
					System.out.println("\nA sequencia de arestas eh a seguinte:");
					my_graph.printEulerTour();
				}


			}
			// Levanta erro caso haja discrepencia em comparacao a entrada esperada e a entra recebida
			catch (IOException e) {
				System.out.println("ERRO--IOException");
				e.printStackTrace();
			}
		}
		// Levanta erro caso haja problemas na criacao ou fechamento do arquivo
		catch (FileNotFoundException e) {
			System.out.println("ERRO--FileNotFound");
			e.printStackTrace();
		}
	}
}
