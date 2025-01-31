package com.practica5.controller.dao;

import com.practica5.controller.tda.grafo.Distancia;
import com.practica5.controller.tda.grafo.GraphLabelDirect;
import com.practica5.controller.tda.list.LinkedList;
import com.practica5.controller.tda.models.City;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.practica5.controller.dao.implement.AdapterDao;
import com.practica5.controller.exception.ListEmptyException;

public class CiudadDao extends AdapterDao<City> {
    private City ciudad;
    private LinkedList<City> listAll;

    public CiudadDao() {
        super(City.class);

    }

    public City getCiudad() {
        if (ciudad == null) {
            ciudad = new City();
        }
        return this.ciudad;
    }

    public void setCiudad(City ciudad) {
        this.ciudad = ciudad;
    }

    public LinkedList<City> getListAll() {
        if (listAll == null) {
            listAll = new LinkedList<>();
        }
        return listAll();
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        ciudad.setId(id);
        this.persist(this.ciudad);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.marge(getCiudad(), getCiudad().getId() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(int id) throws Exception {
        LinkedList<City> list = getListAll();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i).getId() == id) {
                this.Delete(i);
                this.listAll = listAll(); // Actualiza la lista de objetos
                return true; // Retorna verdadero si se eliminó correctamente
            }
        }
        return false; // Retorna falso si no se encontró el ID
    }



    public List<HashMap<String, Object>> generarAdyacenciasAleatorias(GraphLabelDirect<City> graph, LinkedList<City> cities, int numAdyacencias) throws Exception {
        List<HashMap<String, Object>> edgesListWithWeights = new ArrayList<>();

        if (cities == null || cities.isEmpty()) {
            System.out.println("No hay ciudades disponibles para crear adyacencias.");
            return edgesListWithWeights; 
        }

        Random random = new Random();
        City[] cityArray = cities.toArray();

        // Generar adyacencias aleatorias
        for (int i = 0; i < numAdyacencias; i++) {
            int sourceIndex = random.nextInt(cities.getSize());
            int destIndex = random.nextInt(cities.getSize());

            City sourceCity = cityArray[sourceIndex];
            City destCity = cityArray[destIndex];

            // Validar que las ciudades no sean nulas y que no se cree una conexión consigo misma
            if (sourceCity == null || destCity == null || sourceCity.equals(destCity)) {
                continue;
            }

            // Calcular la distancia entre las dos ciudades usando la fórmula Haversine
            double lat1 = sourceCity.getLatitud();
            double lon1 = sourceCity.getLongitud();
            double lat2 = destCity.getLatitud();
            double lon2 = destCity.getLongitud();
            double distance = Distancia.haversine(lat1, lon1, lat2, lon2);

            // Agregar la arista al grafo si no existe
            if (!graph.existe_aristaL(sourceCity, destCity)) {
                graph.insertEdgeL(sourceCity, destCity, (float) distance);

                // Registrar la adyacencia creada
                HashMap<String, Object> edgeWithWeight = new HashMap<>();
                edgeWithWeight.put("from", sourceCity.getName());
                edgeWithWeight.put("to", destCity.getName());
                edgeWithWeight.put("weight", distance);
                edgesListWithWeights.add(edgeWithWeight);

                System.out.println("Arista creada entre " + sourceCity.getName() + " y " + destCity.getName() + " con peso " + distance);
            }
        }

        return edgesListWithWeights;
    }



    public Boolean saveAdyacenciasToFile(List<HashMap<String, String>> edgesListWithoutWeights) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(edgesListWithoutWeights);

            
            String filePath = "media/adyacencias.json";
            File file = new File("media");
            if (!file.exists()) {
                file.mkdir(); 
            }

          
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(json);
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de adyacencias: " + e.getMessage());
            return false;
        }
    }






public Map<City, List<City>> generateAdjacencyList(LinkedList<City> cities) throws IOException {
    Map<City, List<City>> adjacencyList = new HashMap<>();

    // Inicializar la lista de adyacencias con todas las ciudades
    for (City city : cities.toArray()) {
        adjacencyList.put(city, new ArrayList<>());
    }

    // Leer el archivo JSON con las adyacencias
    Gson gson = new Gson();
    String filePath = "media/adyacencias.json";
    Type listType = new TypeToken<List<HashMap<String, String>>>() {}.getType();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        List<HashMap<String, String>> edgesList = gson.fromJson(reader, listType);

        for (HashMap<String, String> edge : edgesList) {
            String from = edge.get("from");
            String to = edge.get("to");

            City sourceCity = null, destCity = null;

            // Buscar las ciudades en la lista
            for (City city : cities.toArray()) {
                if (city.getName().equalsIgnoreCase(from)) {
                    sourceCity = city;
                }
                if (city.getName().equalsIgnoreCase(to)) {
                    destCity = city;
                }
            }

            if (sourceCity != null && destCity != null) {
                adjacencyList.get(sourceCity).add(destCity);
            }
        }
    }

    return adjacencyList;
}






    public float[][] generateAdjacencyMatrix() throws IOException, IndexOutOfBoundsException, ListEmptyException {
        // Obtener todas las ciudades
        LinkedList<City> cities = listAll();
        int size = cities.getSize();
    
        
        float[][] adjacencyMatrix = new float[size][size];
    
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = (i == j) ? 0 : Float.MAX_VALUE;
            }
        }
    
        // Leer el archivo de adyacencias
        Gson gson = new Gson();
        String filePath = "media/adyacencias.json";
        Type listType = new TypeToken<List<HashMap<String, String>>>() {}.getType();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<HashMap<String, String>> edgesList = gson.fromJson(reader, listType);
    
            for (HashMap<String, String> edge : edgesList) {
                String from = edge.get("from");
                String to = edge.get("to");
    
                City sourceCity = null;
                City destCity = null;
    
                // Buscar las ciudades correspondientes
                for (int i = 0; i < cities.getSize(); i++) {
                    City city = cities.get(i);
                    if (city.getName().equalsIgnoreCase(from)) {
                        sourceCity = city;
                    }
                    if (city.getName().equalsIgnoreCase(to)) {
                        destCity = city;
                    }
                }
    
                if (sourceCity != null && destCity != null) {
                    // Calcular la distancia (peso) usando la fórmula Haversine
                    double distance = Distancia.haversine(
                            sourceCity.getLatitud(), sourceCity.getLongitud(),
                            destCity.getLatitud(), destCity.getLongitud());
    
                    // Establecer el peso en la matriz de adyacencia
                    int sourceIndex = cities.indexOf(sourceCity);
                    int destIndex = cities.indexOf(destCity);
                    adjacencyMatrix[sourceIndex][destIndex] = (float) distance;
                }
            }
        }
    
        // Imprimir la matriz de adyacencia en la consola
        printAdjacencyMatrix(adjacencyMatrix, cities);
    
        return adjacencyMatrix;
    }
    
    // Método auxiliar para imprimir la matriz de adyacencia
    private void printAdjacencyMatrix(float[][] adjacencyMatrix, LinkedList<City> cities) throws IndexOutOfBoundsException, ListEmptyException {
        System.out.println("Matriz de adyacencia:");
    
        // Imprimir encabezados de columnas (nombres de ciudades)
        System.out.print("       ");
        for (int i = 0; i < cities.getSize(); i++) {
            City city = cities.get(i);
            System.out.printf("%-10s", city.getName());
        }
        System.out.println();
    
        // Imprimir filas de la matriz
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.out.printf("%-10s", cities.get(i).getName()); // Nombre de la ciudad en la fila
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] == Float.MAX_VALUE) {
                    System.out.printf("%-10s", "INF"); // Mostrar "INF" para valores infinitos
                } else {
                    System.out.printf("%-10.2f", adjacencyMatrix[i][j]); // Mostrar la distancia con 2 decimales
                }
            }
            System.out.println();
        }
    }

}
