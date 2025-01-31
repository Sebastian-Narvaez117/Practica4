package com.practica5.controller.tda.grafo;

import com.practica5.controller.tda.list.LinkedList;

public abstract class Graph {
    public abstract Integer nro_vertices();

    public abstract Integer nro_aristas();

    public abstract Boolean existe_arista(Integer v1, Integer v2) throws Exception;

    public abstract Float peso_arista(Integer v1, Integer v2) throws Exception;

    public abstract void agregar_vertice(Integer v1, Integer v2) throws Exception;

    public abstract void agregar_vertice(Integer v1, Integer v2, Float peso) throws Exception;

    public abstract LinkedList<Adyacencia> adyacencias(Integer v1);

    

    @Override
    public String toString() {
        String grafo = "";
        try {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                grafo += " V " + i + "\n";
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "ady" + "V" + a.getDestino() + " Peso: " + a.getPeso() + "\n";
                    }
                }

            }
        } catch (Exception e) {

        }

        return grafo;

    }








}
