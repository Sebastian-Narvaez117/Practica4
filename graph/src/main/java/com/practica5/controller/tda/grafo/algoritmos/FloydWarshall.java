package com.practica5.controller.tda.grafo.algoritmos;
public class FloydWarshall {
    static final int INF = 99999; // Yo utilizo 99999 en lugar de Integer.MAX_VALUE

    public static void floydWarshall(int[][] graph) {
        int V = graph.length;
        int[][] dist = new int[V][V];

        // Copiamos la matriz original para no modificarla
        for (int i = 0; i < V; i++) {
            System.arraycopy(graph[i], 0, dist[i], 0, V);
        }

        // Medir el tiempo de ejecución
        long startTime = System.nanoTime();

        // Algoritmo de Floyd-Warshall
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF ) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime; // Tiempo de ejecución en nanosegundos

        // Imprimir la matriz resultante
        

        // Mostrar el tiempo de ejecución en milisegundos
        System.out.println("\nTiempo de ejecución: " + elapsedTime / 1_000_000.0 + " ms");
    }



    public static void main(String[] args) {
        // Matriz de adyacencia fija (10 nodos)
        int INF = 99999; // Un valor alto para representar "infinito"  
int[][] graph = {
    {  0,  10, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { 10,   0,  15, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, 15,   0,  20, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, 20,   0,  25, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, 25,   0,  30, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, 30,   0,  35, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, 35,   0,  40, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, 40,   0,  45, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, 45,   0,  50, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, 50,   0,  55, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, 55,   0,  60, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 60,   0,  65, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 65,   0,  70, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 70,   0,  75, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 75,   0,  80, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 80,   0,  85, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 85,   0,  90, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 90,   0,  95, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 95,   0, 100, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 100,   0, 105, INF, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 105,   0, 110, INF, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 110,   0, 115, INF, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 115,   0, 120, INF, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 120,   0, 125, INF, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 125,   0, 130, INF, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 130,   0, 135, INF, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 135,   0, 140, INF, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 140,   0, 145, INF },
    { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 145,   0 }
};


        

        floydWarshall(graph);
    }
}

