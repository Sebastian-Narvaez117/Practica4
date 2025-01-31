package com.practica5.controller.tda.grafo;

public class Distancia {
    // Radio de la Tierra en kilómetros
    private static final double EARTH_RADIUS = 6371.0; //nos ayuda a calcular la distancia entre dos puntos

    public static double haversine(double lat1, double lon1, double lat2, double lon2) { // method haversine
        // Convertir grados a radianes  
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferencias
        double deltaLat = lat2Rad - lat1Rad;  //calcular la diferencia entre las latitudes
        double deltaLon = lon2Rad - lon1Rad;  //calcular la diferencia entre las longitudes

        // Fórmula de Haversine
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +   
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en kilómetros
        return EARTH_RADIUS * c; //multiplicamos la resultante por el radio de la tierra y asi obtenemos la distancia
    }


    public static void main(String[] args) {
        double lat1 = -4.3294;
        double lon1 = -79.55742;
        double lat2 = -4.21689;
        double lon2 = -79.52961;

        double distance = haversine(lat1, lon1, lat2, lon2);
        System.out.println("Distancia entre las dos ciudades: " + distance + " km");
    }
}
