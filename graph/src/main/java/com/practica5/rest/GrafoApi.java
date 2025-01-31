package com.practica5.rest;


import java.util.List;
import java.util.Map;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import com.practica5.controller.dao.CiudadDao;
import com.practica5.controller.tda.models.City;
import com.practica5.controller.tda.list.LinkedList;
import com.practica5.controller.tda.grafo.algoritmos_implementados.BreadthFirstSearch;
import com.practica5.controller.tda.grafo.algoritmos_implementados.FloydWarshall;
import com.practica5.controller.tda.grafo.algoritmos_implementados.BellManFord;
import com.practica5.controller.tda.grafo.GraphLabelDirect;
import com.practica5.controller.dao.services.CiudadServices;



@Path("/graph")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GrafoApi {

    @Path("/añadiradyacencias/{numAdyacencias}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRandomEdgesWithWeights(@PathParam("numAdyacencias") int numAdyacencias) {
        HashMap<String, Object> response = new HashMap<>();
    
        try {
            CiudadServices ciudadService = new CiudadServices();
            LinkedList<City> cities = ciudadService.listAll();
    
            if (cities.isEmpty()) {
                response.put("msg", "No hay ciudades disponibles para crear un grafo con adyacencias.");
                response.put("data", null);
                return Response.status(Response.Status.NO_CONTENT).entity(response).build();
            }
    
            // Crear el grafo
            GraphLabelDirect<City> graph = new GraphLabelDirect<>(cities.getSize(), City.class);
    
            // Etiquetar los vértices
            City[] cityArray = cities.toArray();
            for (int i = 0; i < cities.getSize(); i++) {
                graph.labelsVertices(i + 1, cityArray[i]);
            }
    
            // Llamar al método para generar adyacencias en CiudadDao
            CiudadDao ciudadDao = new CiudadDao();
            List<HashMap<String, Object>> edgesListWithWeights = ciudadDao.generarAdyacenciasAleatorias(graph, cities, numAdyacencias);
    
            // Guardar las adyacencias en un archivo JSON
            List<HashMap<String, String>> edgesListWithoutWeights = new ArrayList<>();
            for (HashMap<String, Object> edge : edgesListWithWeights) {
                HashMap<String, String> edgeWithoutWeight = new HashMap<>();
                edgeWithoutWeight.put("from", (String) edge.get("from"));
                edgeWithoutWeight.put("to", (String) edge.get("to"));
                edgesListWithoutWeights.add(edgeWithoutWeight);
            }
    
            boolean isSaved = ciudadDao.saveAdyacenciasToFile(edgesListWithoutWeights);
    
            if (!isSaved) {
                response.put("msg", "Error al guardar el archivo de adyacencias.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
    
            response.put("msg", "Adyacencias creadas y guardadas correctamente.");
            response.put("data", edgesListWithWeights);
            return Response.ok(response).build();
    
        } catch (Exception e) {
            response.put("msg", "Error al crear adyacencias.");
            response.put("data", e.toString());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }
    
    

    
    @Path("/bfs/{fromCity}/{toCity}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response findPathBFS(@PathParam("fromCity") String fromCity, @PathParam("toCity") String toCity) {
        HashMap<String, Object> response = new HashMap<>();
        
        try {
            CiudadServices ciudadService = new CiudadServices();
            LinkedList<City> cities = ciudadService.listAll();
    
            if (cities.isEmpty()) {
                response.put("msg", "No hay ciudades disponibles.");
                response.put("data", null);
                return Response.status(Response.Status.NO_CONTENT).entity(response).build();
            }
    
            // Generar la lista de adyacencias
            CiudadDao ciudadDao = new CiudadDao();
            Map<City, List<City>> adjacencyList = ciudadDao.generateAdjacencyList(cities);
    
            // Buscar las ciudades de inicio y destino
            City startCity = null, endCity = null;
            for (City city : cities.toArray()) {
                if (city.getName().equalsIgnoreCase(fromCity)) {
                    startCity = city;
                }
                if (city.getName().equalsIgnoreCase(toCity)) {
                    endCity = city;
                }
            }
    
            if (startCity == null || endCity == null) {
                response.put("msg", "Una o ambas ciudades no existen en el grafo.");
                response.put("data", null);
                return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
            }
    
            // Ejecutar BFS
            BreadthFirstSearch bfs = new BreadthFirstSearch(adjacencyList);
            List<String> path = bfs.findPath(startCity, endCity);
    
            if (path.isEmpty()) {
                response.put("msg", "No hay un camino entre las ciudades especificadas.");
                response.put("data", null);
                return Response.status(Response.Status.OK).entity(response).build();
            }

            System.out.println("Camino encontrado usando BFS");
    
            // Construir respuesta
            HashMap<String, Object> pathInfo = new HashMap<>();
            pathInfo.put("from", fromCity);
            pathInfo.put("to", toCity);
            pathInfo.put("path", path);
    
            response.put("msg", "Camino encontrado usando BFS.");
            response.put("data", pathInfo);
    
            return Response.ok(response).build();
    
        } catch (Exception e) {
            response.put("msg", "Error al ejecutar BFS.");
            response.put("data", e.toString());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }
    





    @Path("/floyd-warshall/{fromCity}/{toCity}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculateShortestPathFloyd(
        @PathParam("fromCity") String fromCity,
        @PathParam("toCity") String toCity) {
    
        HashMap<String, Object> response = new HashMap<>();
    
        try {
            CiudadServices ciudadService = new CiudadServices();
            LinkedList<City> cities = ciudadService.listAll();
    
            if (cities.isEmpty()) {
                response.put("msg", "No hay ciudades disponibles para calcular el camino más corto.");
                response.put("data", null);
                return Response.status(Response.Status.NO_CONTENT).entity(response).build();
            }
    
    
            GraphLabelDirect<City> graph = new GraphLabelDirect<>(cities.getSize(), City.class);

            // Etiquetar los vértices
            City[] cityArray = cities.toArray();
            for (int i = 0; i < cities.getSize(); i++) {
                graph.labelsVertices(i + 1, cityArray[i]);
            }
    
            CiudadDao ciudadDao = new CiudadDao();
            ciudadDao.generarAdyacenciasAleatorias(graph, cities, cities.getSize()); // Usar todas las ciudades
    
            // Generar la matriz de adyacencias
            float[][] matrizAdyacencia = ciudadDao.generateAdjacencyMatrix();
    
            // Aplicar Floyd-Warshall
            FloydWarshall floyd = new FloydWarshall(matrizAdyacencia);
            floyd.execute();
    
            // Obtener los índices de las ciudades de origen y destino
            int fromIndex = -1;
            int toIndex = -1;
    
            for (int i = 0; i < cities.getSize(); i++) {
                if (cities.get(i).getName().equalsIgnoreCase(fromCity)) {
                    fromIndex = i;
                }
                if (cities.get(i).getName().equalsIgnoreCase(toCity)) {
                    toIndex = i;
                }
            }
    
            
            if (fromIndex == -1 || toIndex == -1) {
                response.put("msg", "Una o ambas ciudades no existen en el grafo.");
                response.put("data", null);
                return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
            }
    
            // Obtener la distancia más corta
            float distance = floyd.getDistance(fromIndex + 1, toIndex + 1); // Ajustar índices si es necesario
    
            // Verificar si hay un camino válido
            if (distance == Float.MAX_VALUE) {
                response.put("msg", "No hay un camino válido entre las ciudades especificadas.");
                response.put("data", null);
                return Response.status(Response.Status.OK).entity(response).build();
            }
    
            // Reconstruir el camino
            List<String> path = floyd.reconstructPath(fromIndex + 1, toIndex + 1, cities);
            
            System.out.println("Camino más corto encontrado usando Floyd-Warshall");

            // Construir la respuesta
            HashMap<String, Object> pathInfo = new HashMap<>();
            pathInfo.put("from", fromCity);
            pathInfo.put("to", toCity);
            pathInfo.put("distance", distance);
            pathInfo.put("path", path);
    
            response.put("msg", "Camino más corto calculado con éxito.");
            response.put("data", pathInfo);
    
            return Response.ok(response).build();
    
        } catch (Exception e) {
            response.put("msg", "Error al calcular el camino más corto con Floyd-Warshall.");
            response.put("data", e.toString());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }






@Path("/bellman-ford/{fromCity}/{toCity}")
@POST
@Produces(MediaType.APPLICATION_JSON)
public Response calculateShortestPathBellmanFord(
    @PathParam("fromCity") String fromCity,
    @PathParam("toCity") String toCity) {

    HashMap<String, Object> response = new HashMap<>();

    try {
        CiudadServices ciudadService = new CiudadServices();
        LinkedList<City> cities = ciudadService.listAll();

        if (cities.isEmpty()) {
            response.put("msg", "No hay ciudades disponibles para calcular el camino más corto.");
            response.put("data", null);
            return Response.status(Response.Status.NO_CONTENT).entity(response).build();
        }


        GraphLabelDirect<City> graph = new GraphLabelDirect<>(cities.getSize(), City.class);


        City[] cityArray = cities.toArray();
        for (int i = 0; i < cities.getSize(); i++) {
            graph.labelsVertices(i + 1, cityArray[i]);
        }


        CiudadDao ciudadDao = new CiudadDao();
        ciudadDao.generarAdyacenciasAleatorias(graph, cities, cities.getSize()); // Usar todas las ciudades


        float[][] matrizAdyacencia = ciudadDao.generateAdjacencyMatrix();

        
        int fromIndex = -1;
        int toIndex = -1;

        for (int i = 0; i < cities.getSize(); i++) {
            if (cities.get(i).getName().equalsIgnoreCase(fromCity)) {
                fromIndex = i;
            }
            if (cities.get(i).getName().equalsIgnoreCase(toCity)) {
                toIndex = i;
            }
        }


        if (fromIndex == -1 || toIndex == -1) {
            response.put("msg", "Una o ambas ciudades no existen en el grafo.");
            response.put("data", null);
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        // Aplicar Bellman-Ford
        BellManFord bellmanFord = new BellManFord();
        float[] distances = bellmanFord.calcularBellmanFord(matrizAdyacencia, fromIndex);

        // Verificar si hay un camino válido
        if (distances[toIndex] == Float.MAX_VALUE) {
            response.put("msg", "No hay un camino válido entre las ciudades especificadas.");
            response.put("data", null);
            return Response.status(Response.Status.OK).entity(response).build();
        }

        // Reconstruir el camino
        List<String> path = bellmanFord.reconstructPath(fromIndex, toIndex, cities);

        // Construir la respuesta
        HashMap<String, Object> pathInfo = new HashMap<>();
        pathInfo.put("from", fromCity);
        pathInfo.put("to", toCity);
        pathInfo.put("distance", distances[toIndex]);
        pathInfo.put("path", path);


        System.out.println("Camino más corto encontrado usando Bellman-Ford");

        response.put("msg", "Camino más corto calculado con éxito.");
        response.put("data", pathInfo);

        return Response.ok(response).build();

    } catch (Exception e) {
        response.put("msg", "Error al calcular el camino más corto con Bellman-Ford.");
        response.put("data", e.toString());
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }
}










}
