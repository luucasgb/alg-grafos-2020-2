package def;

import java.util.LinkedList;

// Classe usada para modelar o grafo
// Eh pressuposto um grafo nao direcionado
// O grafo eh representado por uma lista encadeada
// Cada elemento da lista representa um vertice e eh tambem uma lista encadeada
// Cada vertice possui a informacao de quais vertices sao seus vizinhos

class Graph {
    // Quantidade de vertices do grafo
    private final int vertices;

    // Lista de adjacencias que define o grafo
    private LinkedList<Integer>[] adjacency_list;

    // Construtor
    Graph(int v) {
        vertices = v;
        adjacency_list = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adjacency_list[i] = new LinkedList();
        }
    }

    // Metodo para adicionar aresta entre os vertices v e w
    void addEdge(int v, int w) {
        adjacency_list[v].add(w);
    }

    // Metodo para remover aresta entre os vertices v e w
    void removeEdge(int v, int w) {
        adjacency_list[v].remove(Integer.valueOf(w));
        adjacency_list[w].remove(Integer.valueOf(v));
    }

    // Metodo para comecar a impressao de caminho/circuito euleriano (caso exista)
    void printEulerTour() {
        // Encontra um vertice de onde iniciar o caminho
        // Inicia-se de um vertice impar, caso exista
        // Caso contrario, inicia-se do primeiro vertice
        int u = 0;
        for (int i = 0; i < vertices; i++) {
            if (adjacency_list[i].size() % 2 == 1) {
                u = i;
                break;
            }
        }
        // Comeca a imprimir a sequencia de arestas partindo do vertice u
        printEulerUtil(u);
        System.out.println();
    }

    // Metodo recursivo para imprimir cada aresta do caminho/circuito euleriano
    private void printEulerUtil(Integer u) {
        for (int i = 0; i < adjacency_list[u].size(); i++) {
            Integer v = adjacency_list[u].get(i);
            if (isValidNextEdge(u, v)) {
                System.out.print(u + "-" + v + " ");
                // Aresta removida depois de ser utilizada para o programa nao entrar em loop
                removeEdge(u, v);
                printEulerUtil(v);
            }
        }
    }

    // Metodo que utiliza busca em profundidade para contar quantos vertices
    // estao conectados a determinado vertice
    private int dfsCount(Integer v, boolean[] isVisited) {
        // Marca o atual vertice como visitado
        isVisited[v] = true;
        int count = 1;
        // Itera para todos vertice adjacentes ao atual
        for (int adjcent : adjacency_list[v]) {
            if (!isVisited[adjcent]) {
                count = count + dfsCount(adjcent, isVisited);
            }
        }
        return count;
    }

    // Metodo que checa testa validade de caminho entre vertices conectados
    // Uma aresta eh considerada valida em dois casos:
    // 1. Se v somente eh adjacente a u
    // 2. Se tiver mais de uma adjacencia, se u e v nao formarem uma ponte
    private boolean isValidNextEdge(Integer u, Integer v) {
        // Se v somente eh adjacente a u
        if (adjacency_list[u].size() == 1) {
            return true;
        }

        // Conta vertices alcancaveis a partir de u
        boolean[] isVisited = new boolean[this.vertices];
        int count1 = dfsCount(u, isVisited);

        // Remove a aresta (u, v) e conta quantos vertices ainda sao alcancaveis
        removeEdge(u, v);
        isVisited = new boolean[this.vertices];
        int count2 = dfsCount(u, isVisited);

        // Restaura o grafo a sua forma original e define se (u, v) eh ponte ou nao
        addEdge(u, v);

        // Retorna false caso count1 seja maior que count2. Caso contrario, retorna true
        return count1 <= count2;
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
        boolean[] visited = new boolean[vertices];
        int i;
        for (i = 0; i < vertices; i++)
            visited[i] = false;

        // Acha um vertice com grau nao zero
        // e se nao restarem arestas, o grafo eh conexo
        for (i = 0; i < vertices; i++)
            if (adjacency_list[i].size() != 0)
                break;
        if (i == vertices)
            return true;

        // Faz uma busca em profundidade para checar cada vertice
        depthFirstSearch(i, visited);

        // Checa se todos os vertices com grau diferente de zero foram visitados
        for (i = 0; i < vertices; i++)
           if (!visited[i] && adjacency_list[i].size() > 0)
                return false;

        return true;
    }

    /* Metodo que retorna a qualidade do grafo da seguinte forma:
       0 - O grafo eh nao euleriano
       1 - O grafo eh semi-euleriano (Tem um caminho euleriano)
       2 - O grafo eh euleriano (Tem um circuito euleriano)   */

    int isEulerian() {
        // Checa se o grafo eh conexo
        if (!isConnected())
            return 0;

        // Conta quantos vertices impares o grafo possui
        int odd = 0;
        for (int i = 0; i < vertices; i++) {
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
        // Se o grafo nao tiver vertices impares, o grafo possui um circuito euleriano.
        else
            return (odd == 2) ? 1 : 2;
    }

    // Metodo que realiza o teste de qualidade do grafo
    boolean getClassification() {
        int result = isEulerian();
        if (result == 1) {
            System.out.println("\nHa caminho euleriano");
            return true;
        }
        else if (result == 2) {
            System.out.println("\nHa circuito euleriano");
            return true;
        }
        else {
            System.out.println("\nNao ha circuito ou caminho euleriano");
            return false;
        }
    }
}

