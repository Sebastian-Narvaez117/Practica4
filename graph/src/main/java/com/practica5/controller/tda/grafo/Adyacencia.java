package com.practica5.controller.tda.grafo;

public class Adyacencia {
    private Integer destino;
    private Float peso;
    private Double distancia;


   


    public Adyacencia(Integer destino, Float peso) {
        this.destino = destino;
        this.peso = peso;
    }


    public Adyacencia() {}
    


    public Integer getDestino() {
        return destino;
    }
    public void setDestino(Integer destino) {
        this.destino = destino;
    }
    public Float getPeso() {
        return peso;
    }
    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Double getDistancia() {
        return distancia;
    }


    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }
    
    

    
}
