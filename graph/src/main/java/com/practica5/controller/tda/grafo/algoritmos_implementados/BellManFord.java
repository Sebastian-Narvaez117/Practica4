package com.practica5.controller.tda.grafo.algoritmos_implementados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.practica5.controller.exception.ListEmptyException;
import com.practica5.controller.tda.list.LinkedList;
import com.practica5.controller.tda.models.City;

public class BellManFord {

    private int[] prev; 

    public float[] calcularBellmanFord(float[][] matrizAdyacencia, int source) {
        int n = matrizAdyacencia.length;
        float[] dist = new float[n];
        prev = new int[n];

        
        for (int i = 0; i < n; i++) {
            dist[i] = Float.MAX_VALUE;
            prev[i] = -1; 
        }
        dist[source] = 0;

        // 
        for (int k = 0; k < n - 1; k++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (matrizAdyacencia[u][v] != Float.MAX_VALUE && dist[u] != Float.MAX_VALUE) {
                        if (dist[v] > dist[u] + matrizAdyacencia[u][v]) {
                            dist[v] = dist[u] + matrizAdyacencia[u][v];
                            prev[v] = u; 
                        }
                    }
                }
            }
        }

        // Verificar ciclos negativos 
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (matrizAdyacencia[u][v] != Float.MAX_VALUE && dist[u] != Float.MAX_VALUE &&
                    dist[u] + matrizAdyacencia[u][v] < dist[v]) {
                    throw new IllegalStateException("El grafo contiene ciclos negativos");
                }
            }
        }

        return dist;
    }

    // Método para reconstruir el camino
    public List<String> reconstructPath(int start, int end, LinkedList<City> cities) throws IndexOutOfBoundsException, ListEmptyException {
        List<String> path = new ArrayList<>();
        if (prev[end] == -1) {
            return path; // No hay camino
        }

        // Reconstruir el camino desde el final hasta el inicio
        int current = end;
        while (current != start) {
            path.add(cities.get(current).getName());
            current = prev[current];
        }
        path.add(cities.get(start).getName()); // Agregar la ciudad de inicio

        // Invertir el camino para que esté en orden correcto
        Collections.reverse(path);
        return path;
    }






//Probar el algoritmo de Bellman-Ford con un grafo de ejemplo






















}