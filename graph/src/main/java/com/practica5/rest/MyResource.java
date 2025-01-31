package com.practica5.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.practica5.controller.dao.services.CiudadServices;
import com.practica5.controller.tda.grafo.GraphLabelDirect;
import com.practica5.controller.tda.list.LinkedList;
import com.practica5.controller.tda.models.City;

@Path("test")
public class MyResource {
    @SuppressWarnings("unchecked")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        HashMap<String, Object> mapa = new HashMap<>();
        @SuppressWarnings("rawtypes")
        GraphLabelDirect grafo = null;
        try {
            CiudadServices ps = new CiudadServices();
            LinkedList<City> ciudades = ps.listAll();
            if (!ciudades.isEmpty()) {
                grafo = new GraphLabelDirect<>(ciudades.getSize(), City.class);
                City[] m = ciudades.toArray();
                for (int i = 0; i < ciudades.getSize(); i++) {
                    grafo.labelsVertices((i + 1), m[i]);
                }
            }
        } catch (Exception e) {

            mapa.put("msg", "Error");
            mapa.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(mapa).build();
        }
        System.out.println(grafo.toString());
        mapa.put("msg", "OK");
        mapa.put("data", grafo.toString());
        return Response.ok(mapa).build();
    }


}
