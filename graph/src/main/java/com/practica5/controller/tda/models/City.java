package com.practica5.controller.tda.models;

public class City {
    private Integer id;
    private String nombre;
    private Integer habitantes;
    private Double latitud;
    private Double longitud;
    

    public City() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return nombre;
    }

    public Integer getHabitantes() {
        return habitantes;
    }

    public void setName(String name) {
        this.nombre = name;
    }

    public void setHabitantes(Integer habitantes) {
        this.habitantes = habitantes;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return nombre + " " +
                habitantes;
    }
    
}
