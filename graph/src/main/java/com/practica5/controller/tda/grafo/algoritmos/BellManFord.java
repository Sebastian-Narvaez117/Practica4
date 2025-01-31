package com.practica5.controller.tda.grafo.algoritmos;

public class BellManFord {
    static final int INF = 99999; // Valor para representar "infinito"
    static final int N = 30;      // Número de nodos

    public static void bellmanFord(int[][] graph, int source) {
        // Arreglo para almacenar las distancias desde el nodo fuente
        int[] dist = new int[N];

        // Inicializar todas las distancias como "infinito", excepto el nodo fuente
        for (int i = 0; i < N; i++) {
            dist[i] = INF;
        }
        dist[source] = 0; // La distancia del nodo fuente a sí mismo es 0

        // Medir el tiempo de ejecución
        long startTime = System.nanoTime();

        // Relajar todas las aristas (N - 1) veces
        for (int i = 0; i < N - 1; i++) {
            for (int u = 0; u < N; u++) {
                for (int v = 0; v < N; v++) {
                    if (graph[u][v] != INF && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v]; // Actualizar la distancia
                    }
                }
            }
        }

        // Verificar ciclos negativos 
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                if (graph[u][v] != INF && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
                    System.out.println("El grafo contiene ciclos negativos.");
                    return;
                }
            }
        }

        // Calcular el tiempo de ejecución
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        // Mostrar el tiempo de ejecución
        System.out.println("Tiempo de ejecución: " + elapsedTime / 1_000_000.0 + " ms");

       
    }

    public static void main(String[] args) {
        // Grafo de ejemplo con 30 nodos
        int INF = 99999; // Valor para representar "infinito"
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
            { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 145,   0, INF },
            { INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, INF, 0 }
        };

        int source = 0; // Nodo fuente
        bellmanFord(graph, source);
    }
}