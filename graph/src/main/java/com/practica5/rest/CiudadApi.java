package com.practica5.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.practica5.controller.dao.services.CiudadServices;

    @Path("city")
    public class CiudadApi {
        @Path("/list")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getAllCitis() {
            HashMap<String, Object> map = new HashMap<>();
            CiudadServices ps = new CiudadServices();
            System.out.println("List size: " + ps.listAll().getSize()); // Imprimir tamaño de la lista

            map.put("msg", "OK");
            map.put("data", ps.listAll().toArray());

            if (ps.listAll().isEmpty()) {
                map.put("data", new Object[] {});
                return Response.status(Status.BAD_REQUEST).entity(map).build();
            }

            return Response.ok(map).build();
        }


        @Path("/get/{id}")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getPersons(@PathParam("id") Integer id) {
            HashMap<String, Object> map = new HashMap<>();
            CiudadServices ps = new CiudadServices();
            try {

                ps.setCiudad(ps.get(id));
            } catch (Exception e) {

            }
            map.put("msg", "OK");
            map.put("data", ps.getCiudad());

            if (ps.getCiudad().getId() == null) {
                map.put("data", "NO existe la Ciudad con ese identificador");
                return Response.status(Status.BAD_REQUEST).entity(map).build();
            }

            return Response.ok(map).build();
        }


        @Path("/save")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response save(HashMap<String, Object> map) {
            // todo
            // VALIDATIONS -- bad request
            HashMap<String, Object> res = new HashMap<>();
            Gson g = new Gson();
            String a = g.toJson(map);
            System.out.println("**********" + a);

            try {
                CiudadServices ps = new CiudadServices();
                ps.getCiudad().setName(map.get("nombre").toString());
                ps.getCiudad().setHabitantes(Integer.parseInt(map.get("habitantes").toString()));
                ps.getCiudad().setLatitud(Double.parseDouble(map.get("lat").toString()));
                ps.getCiudad().setLongitud(Double.parseDouble(map.get("lon").toString()));
                ps.save();
                res.put("msg", "OK");
                res.put("data", "Ciudad guardada correctamente");
                return Response.ok(res).build();

            } catch (Exception e) {
                System.out.println("ERROR: " + e.toString());
                res.put("msg", "ERROR");
                res.put("data", e.toString());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
            }

        }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        // todo
        // VALIDATIONS -- bad request
        HashMap<String, Object> res = new HashMap<>();

        try {
            CiudadServices ps = new CiudadServices();
            ps.getCiudad().setId(Integer.parseInt(map.get("id").toString()));
            ps.getCiudad().setName(map.get(("nombre")).toString());
            ps.getCiudad().setHabitantes(Integer.parseInt(map.get("habitantes").toString()));

            ps.update();
            res.put("msg", "OK");
            res.put("data", "Ciudad guardada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("ERROR: " + e.toString());
            System.out.println("Esto se esta ejecutando");
            res.put("msg", "ERROR");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }

    }


    @Path("/delete/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        CiudadServices ps = new CiudadServices();
        try {
            ps.delete(id);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.toString());
        }
        map.put("msg", "OK");
        map.put("data", "Ciudad eliminada correctamente");

        return Response.ok(map).build();
    }







}
