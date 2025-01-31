document.addEventListener("DOMContentLoaded", function () {
    var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png';
    var osmAttrib = '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors';
    var osm = L.tileLayer(osmUrl, { maxZoom: 15, attribution: osmAttrib });

    var map = L.map('map').setView([-3.99313, -79.20422], 15);
    osm.addTo(map);

    var cityMap = {}; // Guardar ID de ciudad con sus coordenadas
    var adyacenciasDibujadas = []; // Para almacenar las líneas dibujadas
    var shortestPathLayer = null; // Capa para el camino más corto

    if (typeof ciudades !== 'undefined' && ciudades.length > 0) {
        ciudades.forEach(function (ciudad) {
            var lat = ciudad.latitud;
            var lon = ciudad.longitud;
            var nombre = ciudad.name;

            if (!lat || !lon) {
                console.error(`Coordenadas inválidas para ${nombre}:`, ciudad);
                return;
            }

            cityMap[ciudad.id] = [lat, lon];

            var marker = L.marker([lat, lon]).addTo(map);
            marker.bindPopup(`<b>${nombre}</b><br>Latitud: ${lat}<br>Longitud: ${lon}`);
        });
    } else {
        console.error("No se encontraron ciudades para graficar.");
    }

    // Función para dibujar el camino más corto
    function drawShortestPath(path, color = 'red') {
        if (shortestPathLayer) {
            map.removeLayer(shortestPathLayer); // Eliminar el camino anterior
        }

        var latLngs = path.map(cityId => cityMap[cityId]);
        shortestPathLayer = L.polyline(latLngs, { color: color }).addTo(map);
    }

    document.getElementById("generarAdyacencias").addEventListener("click", function () {
        console.log("Generando adyacencias...");
        
        // Eliminar adyacencias previas si existen
        adyacenciasDibujadas.forEach(polyline => map.removeLayer(polyline));    
        adyacenciasDibujadas = [];

        // Obtener las adyacencias desde Flask
        fetch('/admin/graph/edges')
            .then(response => response.json())
            .then(data => {
                if (data.adyacencias) {
                    data.adyacencias.forEach(adyacencia => {
                        var origen = cityMap[adyacencia.origen];
                        var destino = cityMap[adyacencia.destino];

                        if (origen && destino) {
                            var polyline = L.polyline([origen, destino], { color: 'black' }).addTo(map);
                            polyline.bindPopup(`Peso: ${adyacencia.peso}`);
                            adyacenciasDibujadas.push(polyline);
                        } else {
                            console.error("No se encontraron coordenadas para una adyacencia:", adyacencia);
                        }
                    });
                } else {
                    console.error("No se encontraron adyacencias.");
                }
            })
            .catch(error => console.error("Error obteniendo adyacencias:", error));
    });
   
});