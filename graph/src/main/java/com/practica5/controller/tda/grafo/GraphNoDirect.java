package com.practica5.controller.tda.grafo;

import com.practica5.controller.exception.OverFlowException;

public class GraphNoDirect extends GraphDirect {

    public GraphNoDirect(Integer nro_vertices) {
        super(nro_vertices);
    }

    public void agregar_vertice(Integer v1, Integer v2, Float peso) throws Exception {
        if (v1.intValue() <= nro_vertices() && v2.intValue() <= nro_vertices()) {
            if (!existe_arista(v1, v2)) {
                setNro_aristas(nro_aristas() + 1);

                Adyacencia aux = new Adyacencia();
                aux.setPeso(peso);
                aux.setDestino(v2);
                getListAdyacencias()[v1].add(aux);

                Adyacencia auxD = new Adyacencia();
                aux.setPeso(peso);
                aux.setDestino(v1);
                getListAdyacencias()[v2].add(auxD);
            }
        } else {
            throw new OverFlowException("Los vertices estan fuera de rango");
        }
    }

}
