package def;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
				int n = 0;
				while(count.readLine() != null) n++;
				my_graph = new Graph(n);
				count.close();

				// Le cada linha da entrada e adiciona cada vertice reconhecido ao grafo
				// A entrada eh divida em duas partes utilizando o '=' como separador
				// Cada vertice apos o '=' eh conectado entao ao vertice antes do '='
				while((line = br.readLine()) != null) {
					String[] lineList= line.split(" = ");
					int v = Integer.parseInt( (lineList[0].replaceAll(" ", "")) );
					String[] adjList= lineList[1].split(" ");
					for(String wstr : adjList) {
						int w = Integer.parseInt(wstr);
						my_graph.addEdge(v-1, w-1);
					}
				}

				// Finaliza o processo de leitura
				br.close();

				// Roda o teste programado no arquivo "Grafo.java"
				my_graph.getClassification();

			// Levanta erro caso haja discrepencia em comparacao a entrada esperada e a entra recebida
			} catch (IOException e) {
				System.out.println("ERRO--IOException");
				e.printStackTrace();
			}

		// Levanta erro caso haja problemas na criacao ou fechamento do arquivo
		} catch (FileNotFoundException e) {
			System.out.println("ERRO--FileNotFound");
			e.printStackTrace();
		}

	}

}
