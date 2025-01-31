package com.practica5.controller.tda.grafo;

import com.practica5.controller.exception.OverFlowException;
import com.practica5.controller.tda.list.LinkedList;

public class GraphDirect extends Graph {
    private Integer nro_vertices;
    private Integer nro_aristas;

    private LinkedList<Adyacencia> listAdyacencias[];

    @SuppressWarnings("unchecked")
    public GraphDirect(int nroVertices) {
        this.nro_vertices = nroVertices;
        this.listAdyacencias = new LinkedList[nroVertices + 1]; // Tamaño ajustado
        this.nro_aristas = 0;
        System.out.println("Tamaño de la lista de adyacencias: " + listAdyacencias.length);
        for (int i = 1; i <= nroVertices; i++) {
            this.listAdyacencias[i] = new LinkedList<>();
        }
    }
    
    

    public Integer nro_aristas() {
        return this.nro_aristas;
    }

    public Integer nro_vertices() {
        return this.nro_vertices;
    }

    public Boolean existe_arista(Integer v1, Integer v2) throws Exception {
        Boolean band = false;
        if (v1.intValue() <= nro_vertices && v2.intValue() <= nro_vertices) {
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if (!lista.isEmpty()) {
                Adyacencia[] matrix = lista.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if (aux.getDestino().intValue() == v2.intValue()) {
                        band = true;
                        break;
                    }
                }
            }
        } else {
            throw new OverFlowException("Los vertices estan fuera de rango");
        }
        return band;

    }

    public Float peso_arista(Integer v1, Integer v2) throws Exception {
        Float peso = Float.NaN;
        if (existe_arista(v1, v2)) {
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if (!lista.isEmpty()) {
                Adyacencia[] matrix = lista.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if (aux.getDestino().intValue() == v2.intValue()) {
                        peso = aux.getPeso();
                        break;
                    }

                }
            }
        }
        return peso;
    }

    public void agregar_vertice(Integer v1, Integer v2, Float peso) throws Exception {
        if (v1.intValue() <= nro_vertices && v2.intValue() <= nro_vertices) {
            if (!existe_arista(v1, v2)) {
                nro_aristas++;
                Adyacencia aux = new Adyacencia();
                aux.setPeso(peso);
                aux.setDestino(v2);
                listAdyacencias[v1].add(aux);
                
            }
        } else {
            throw new OverFlowException("Los vertices estan fuera de rango");
        }
    }


    public void agregar_Vertice(Integer v1, Integer v2, Double peso) throws Exception {
        if (v1.intValue() <= nro_vertices && v2.intValue() <= nro_vertices) {
            if (!existe_arista(v1, v2)) {
                nro_aristas++;
                Adyacencia aux = new Adyacencia();
                aux.setDistancia(peso);
                aux.setDestino(v2);
                listAdyacencias[v1].add(aux);
                
            }
        } else {
            throw new OverFlowException("Los vertices estan fuera de rango");
        }
    }




    public void agregar_vertice(Integer v1, Integer v2) throws Exception {
        this.agregar_vertice(v1, v2, Float.NaN);
    }

    public LinkedList<Adyacencia> adyacencias(Integer v1) {
        return listAdyacencias[v1];
    }

    public LinkedList<Adyacencia>[] getListAdyacencias() {
        return listAdyacencias;
    }

    public void setNro_aristas(Integer nro_aristas) {
        this.nro_aristas = nro_aristas;
    }

}
