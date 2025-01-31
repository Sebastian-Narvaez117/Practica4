package com.practica5.controller.tda.grafo.algoritmos_implementados;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.practica5.controller.exception.ListEmptyException;
import com.practica5.controller.tda.list.LinkedList;
import com.practica5.controller.tda.models.City;

public class FloydWarshall {
    private float[][] dist;
    private int[][] next; // Matriz de predecesores

    public FloydWarshall(float[][] matrizAdyacencia) {
        int n = matrizAdyacencia.length;
        dist = new float[n][n];
        next = new int[n][n];

        // Inicializar distancias y predecesores
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = matrizAdyacencia[i][j];
                if (matrizAdyacencia[i][j] != Float.MAX_VALUE) {
                    next[i][j] = j; // El predecesor de j es i
                } else {
                    next[i][j] = -1; // No hay camino
                }
            }
        }
    }

    public FloydWarshall() {
            
        }
    
        public void execute() {
            int n = dist.length;
    
            // Aplicar Floyd-Warshall
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (dist[i][k] != Float.MAX_VALUE && dist[k][j] != Float.MAX_VALUE) {
                            if (dist[i][j] > dist[i][k] + dist[k][j]) {
                                dist[i][j] = dist[i][k] + dist[k][j];
                                next[i][j] = next[i][k]; // Actualizar predecesor
                            }
                        }
                    }
                }
            }
        }
    
        public float getDistance(int i, int j) {
            return dist[i - 1][j - 1]; // Ajustar índices si es necesario
        }
    
        // Método para reconstruir el camino
        public List<String> reconstructPath(int start, int end, LinkedList<City> cities) throws IndexOutOfBoundsException, ListEmptyException {
            List<String> path = new ArrayList<>();
            if (next[start - 1][end - 1] == -1) {
                return path; // No hay camino
            }
    
            int current = start - 1;
            path.add(cities.get(current).getName()); // Agregar la ciudad de inicio
    
            while (current != end - 1) {
                current = next[current][end - 1];
                path.add(cities.get(current).getName());
            }
    
            return path;
        }
    
    
        public float[][] ejecutarFloydWarshall(float[][] matrizAdyacencia) {
            int n = matrizAdyacencia.length;
            float[][] dist = new float[n][n];
    
            // Inicializar distancias
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = matrizAdyacencia[i][j];
                }
            }
    
            // Aplicar el algoritmo de Floyd-Warshall
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (dist[i][k] != Float.MAX_VALUE && dist[k][j] != Float.MAX_VALUE) {
                            if (dist[i][j] > dist[i][k] + dist[k][j]) {
                                dist[i][j] = dist[i][k] + dist[k][j];
                            }
                        }
                    }
                }
            }
    
            return dist;
        }
    
    
        public static float[][] generarMatrizAdyacencia(int n) {
        float[][] matriz = new float[n][n];
        Random random = new Random();
    
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matriz[i][j] = 0; // La distancia de un nodo a sí mismo es 0
                } else {
                    // Generar un valor aleatorio entre 1 y 100, o Float.MAX_VALUE para representar "infinito"
                    matriz[i][j] = random.nextFloat() < 0.7 ? random.nextInt(100) + 1 : Float.MAX_VALUE;
                }
            }
        }
    
        return matriz;
    }
    
    
    
    
}


