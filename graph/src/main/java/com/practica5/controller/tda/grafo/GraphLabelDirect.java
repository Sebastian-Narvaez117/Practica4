package com.practica5.controller.tda.grafo;

import java.lang.reflect.Array;
import java.util.HashMap;
import com.practica5.controller.exception.LabelException;
import com.practica5.controller.tda.list.LinkedList;

public class GraphLabelDirect<E> extends GraphDirect {
    protected E labels[];
    protected HashMap<E, Integer> dicVertices;

    @SuppressWarnings("unused")
    private Class<E> clazz;

   
    

    @SuppressWarnings("unchecked")
    public GraphLabelDirect(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices);
        this.clazz = clazz;
        labels = (E[]) Array.newInstance(clazz, nro_vertices + 1);
        dicVertices = new HashMap<>();
    }

    public Boolean existe_aristaL(E v1, E v2) throws Exception {
        if (v1 == null || v2 == null) {
            throw new LabelException("No se puede verificar la arista: una o ambas ciudades son null.");
        }
    
        if (isLabelsGraph()) {
            Integer v1Index = getVerticesL(v1);
            Integer v2Index = getVerticesL(v2);
    
            if (v1Index == null || v2Index == null) {
                throw new LabelException("No se pudo encontrar uno o ambos vértices en el grafo.");
            }
    
            return existe_arista(v1Index, v2Index);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }
    
    

    public void insertEdgeL(E v1, E v2, Float peso) throws Exception {
        if (isLabelsGraph()) {
            agregar_vertice(getVerticesL(v1), getVerticesL(v2), peso);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }



    public void insertedgeL(E v1, E v2, Double peso) throws Exception {
        if (isLabelsGraph()) {
            agregar_Vertice(getVerticesL(v1), getVerticesL(v2), peso);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }



    public LinkedList<Adyacencia> adyacenciasL(E v) throws Exception {
        if (isLabelsGraph()) {
            return adyacencias(getVerticesL(v));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            insertEdgeL(v1, v2, Float.NaN);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void labelsVertices(Integer v1, E data) {
        labels[v1] = data;
        dicVertices.put(data, v1);

    }

    public Boolean isLabelsGraph() {
        Boolean band = true;
        for (int i = 1; i < labels.length; i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }
        }
        return band;
    }

    
    

    public LinkedList<HashMap<String, Object>> getEdges() {
        LinkedList<HashMap<String, Object>> edges = new LinkedList<>();
        try {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                LinkedList<Adyacencia> listaAdyacencias = adyacencias(i);
                if (!listaAdyacencias.isEmpty()) {
                    Adyacencia[] ady = listaAdyacencias.toArray();
                    for (Adyacencia a : ady) {
                        HashMap<String, Object> edge = new HashMap<>();
                        edge.put("from", i);
                        edge.put("to", a.getDestino());
                        edge.put("weight", a.getPeso());
                        edges.add(edge);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return edges;
    }

    public Integer getVerticesL(E label) {
        return dicVertices.get(label);
    }

    public E getLabelL(Integer v1) {
        if (v1 < 1 || v1 >= labels.length) {
            throw new IllegalArgumentException("Índice de vértice fuera de rango: " + v1);
        }
        return labels[v1];
    }


    @Override
    public String toString() {
        String grafo = "";
        try {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                grafo += " V " + i + "[" + getLabelL(i).toString() + "]" + "\n";
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "ady" + "V" + a.getDestino() + " Peso: " + a.getPeso() + "["
                                + getLabelL(a.getDestino()).toString() + "]" + "\n";
                    }
                }

            }
        } catch (Exception e) {

        }

        return grafo;
    }





public Float getWeightL(E v1, E v2) throws Exception {
    // Verificar si el grafo tiene etiquetas
    if (!isLabelsGraph()) {
        throw new LabelException("El grafo no está etiquetado.");
    }

    // Obtener los índices de los vértices de las etiquetas
    Integer v1Index = getVerticesL(v1);
    Integer v2Index = getVerticesL(v2);

    if (v1Index == null || v2Index == null) {
        throw new LabelException("No se pudo encontrar uno o ambos vértices en el grafo.");
    }

    // Buscar las adyacencias del vértice de origen (v1Index)
    LinkedList<Adyacencia> adyacencias = adyacencias(v1Index);

    // Iterar sobre las adyacencias para encontrar la arista correspondiente
    for (Adyacencia adyacencia : adyacencias.toArray()) {
        if (adyacencia.getDestino().equals(v2Index)) {
            return adyacencia.getPeso();  // Retornar el peso de la arista
        }
    }

    // Si no se encuentra la arista, lanzar una excepción o retornar un valor indicativo
    throw new LabelException("No existe una arista entre los vértices " + v1 + " y " + v2);
}



}