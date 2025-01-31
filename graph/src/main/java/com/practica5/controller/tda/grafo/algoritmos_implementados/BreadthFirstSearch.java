package com.practica5.controller.tda.grafo.algoritmos_implementados;

import java.util.*;

import com.practica5.controller.tda.models.City;

public class BreadthFirstSearch {
    private Map<City, List<City>> adjacencyList;

    public BreadthFirstSearch(Map<City, List<City>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public List<String> findPath(City start, City goal) {
        Queue<City> queue = new LinkedList<>();
        Map<City, City> predecessors = new HashMap<>();
        Set<City> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        predecessors.put(start, null);

        while (!queue.isEmpty()) {
            City current = queue.poll();
            
            if (current.equals(goal)) {
                return reconstructPath(predecessors, goal);
            }

            for (City neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    predecessors.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList(); // Retorna lista vacía si no hay camino
    }

    private List<String> reconstructPath(Map<City, City> predecessors, City goal) {
        List<String> path = new ArrayList<>();
        for (City at = goal; at != null; at = predecessors.get(at)) {
            path.add(at.getName());
        }
        Collections.reverse(path);
        return path;
    }
}

