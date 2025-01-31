package com.practica5.controller.tda.grafo;

import com.practica5.controller.exception.LabelException;

public class GraphLabelNoDirect<E> extends GraphLabelDirect<E> {
    public GraphLabelNoDirect(Integer nro_vertices, Class<E> clazz){
        super(nro_vertices, clazz);
    }

    public void insertEdgeL(E v1, E v2, Float peso) throws Exception {
        if (isLabelsGraph()) {
            agregar_vertice(getVerticesL(v1), getVerticesL(v2), peso);
            agregar_vertice(getVerticesL(v2), getVerticesL(v1), peso);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    
    
}
