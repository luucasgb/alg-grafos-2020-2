package def;

import java.util.LinkedList;

// Classe usada para modelar o grafo
// Eh pressuposto um grafo nao direcionado
// O grafo eh representado por uma lista encadeada
// Cada elemento da lista representa um vertice e eh tambem uma lista encadeada
// Cada vertice possui a informacao de quais vertices sao seus vizinhos

class Graph
{
    // Quantidade de vertices do grafo
    private final int V;

    // Lista de adjacencias que define o grafo
    private LinkedList<Integer>[] adjacency_list;

    // Construtor
    Graph(int v) {
        V = v;
        adjacency_list = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adjacency_list[i] = new LinkedList();
        }
    }

    // Metodo para adicionar aresta entre os vertices v e w
    void addEdge(int v, int w) {
        adjacency_list[v].add(w);
    }

    // Metodo para fazer busca em profundidade
    void depthFirstSearch(int v, boolean[] visited) {
        // Comeca no primeiro vertice e o marca como visitado
        visited[v] = true;
        // Recorre para todos os vertices adjacentes ao atual
        for (int n : adjacency_list[v]) {
            if (!visited[n])
                depthFirstSearch(n, visited);
        }
    }

    // Metodo que checa se todos os vertices
    // possuem grau diferente de zero
    boolean isConnected() {
        // Marca todos os vertices como nao visitados
        // Essa condicao eh atualizada posteriormente conforme necessario
        boolean[] visited = new boolean[V];
        int i;
        for (i = 0; i < V; i++)
            visited[i] = false;

        // Acha um vertice com grau nao zero
        // e se nao restarem arestas, o grafo eh conexo
        for (i = 0; i < V; i++)
            if (adjacency_list[i].size() != 0)
                break;
        if (i == V)
            return true;

        // Faz uma busca em profundidade para checar cada vertice
        depthFirstSearch(i, visited);

        // Checa se todos os vertices com grau diferente de zero foram visitados
        for (i = 0; i < V; i++)
           if (!visited[i] && adjacency_list[i].size() > 0)
                return false;

        return true;
    }

    /* Metodo que retorna a qualidade do grafo da seguinte forma
       0 - O grafo eh nao euleriano
       1 - O grafo eh semi-euleriano (Tem um caminho euleriano)
       2 - O grafo eh euleriano (Tem um circuito euleriano)   */

    int isEulerian() {
        // Checa se o grafo eh conexo
        if (!isConnected())
            return 0;

        // Conta quantos vertices impares o grafo possui
        int odd = 0;
        for (int i = 0; i < V; i++) {
            System.out.println("Vizinhanca do vertice " + (i) + ": " + adjacency_list[i]);
            if (adjacency_list[i].size()%2!=0) {
                odd++;
            }
        }

        System.out.println("\nQuantidade de vertices impares: " + odd);

        // Se a quantidade de vertices impares for maior que 2, o grafo nao eh euleriano.
        if(odd > 2) {
            return 0;
        }

        // Se a quantidade de vertices impares for igual a 2, o grafo possui um caminho euleriano.
        // Se o grafo nao possuir nenhum vertice impar, o grafo eh euleriano.
        else
            return (odd == 2) ? 1 : 2;
    }

    // Metodo que realiza o teste de qualidade do grafo
    void getClassification() {
        System.out.println("Numero de vertices V: " + V +"\n");
        int result = isEulerian();
        if (result == 1) {
            System.out.println("\nHa caminho euleriano");
        }
        else if (result == 2) {
            System.out.println("\nHa circuito euleriano");
        }
        else {
            System.out.println("\nNao ha circuito euleriano");
        }
    }
}

