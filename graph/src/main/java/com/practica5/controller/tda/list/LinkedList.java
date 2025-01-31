package com.practica5.controller.tda.list;


import java.util.ArrayList;

import com.practica5.controller.exception.ListEmptyException;

public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;
    

    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0; // Inicializa size a 0
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);

    }

    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            this.header = help;
            this.last = help; // Inicializa last cuando se agrega el primer nodo
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addHeader(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            this.size++;
        }
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }


    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empity");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

 

    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("List Data \n");
        try{

            Node<E> help = header;
            while(help != null){
                sb.append(help.getInfo()).append("->");
                help = help.getNext();
            }
        } catch(Exception e){
            sb.append(e.getMessage());
        }

        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }


    @SuppressWarnings("unchecked")
    public E[] toArray() {
        E[] matriz = null;
        if (!isEmpty()) {
            @SuppressWarnings("rawtypes")
            Class clazz = header.getInfo().getClass();
            matriz = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matriz[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matriz;
    }


    @SuppressWarnings("unchecked")
    public E[] toarray() {
        E[] matriz = null;
        
        if (!isEmpty()) {
            // Asegurarse de que se obtiene la clase correctamente del primer nodo
            @SuppressWarnings("rawtypes")
            Class clazz = header.getInfo().getClass();
            
            // Crear un arreglo con el tamaño correcto
            matriz = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            
            Node<E> aux = header;
            
            // Rellenar el arreglo con los elementos de la lista
            for (int i = 0; i < this.size; i++) {
                if (aux != null) {
                    matriz[i] = aux.getInfo();  // Asignar el valor del nodo al arreglo
                    aux = aux.getNext();  // Avanzar al siguiente nodo
                } else {
                    // Si hay un nodo faltante, lanzamos una excepción para evitar errores inesperados
                    throw new IllegalStateException("El nodo no existe en la posición esperada");
                }
            }
        } else {
            // Si la lista está vacía, devolvemos un arreglo vacío
            matriz = (E[]) java.lang.reflect.Array.newInstance(Object.class, 0);
        }
    
        return matriz;
    }
    





    public ArrayList<E> toArrayList() {
    ArrayList<E> list = new ArrayList<>();
    Node<E> aux = header;

    while (aux != null) {
        list.add(aux.getInfo());
        aux = aux.getNext();
    }

    return list;
}

    public LinkedList<E> toList(E[] matriz) {
        reset();
        for (int i = 0; i < matriz.length; i++) {
            this.add(matriz[i]);
        }
        return this;
    }

    

    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }

    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }

    public E delete(Integer post) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (post == 0) {
            return deleteFirst();
        } else if (post == (size - 1)) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(post - 1);
            Node<E> actually = getNode(post);
            E element = preview.getInfo();
            Node<E> next = actually.getNext();
            actually = null;
            preview.setNext(next);
            size--;
            return element;
        }

    }

    public void update(E data, Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            header.setInfo(data);
        } else if (post == (size - 1)) {
            last.setInfo(data);
        } else {
            // 2 5 6 9 --> 2
            Node<E> search = header;
            Integer cont = 0;
            while (cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }

    public Node<E> getHeader() {
        return this.header;
    }
    

    public int indexOf(E element) {
        if (isEmpty()) {
            return -1; // Si la lista está vacía, devolvemos -1
        }
    
        Node<E> current = header; // Empezamos desde el primer nodo
        int index = 0;
    
        while (current != null) {
            if (current.getInfo().equals(element)) {
                return index; // Si encontramos el elemento, devolvemos el índice
            }
            current = current.getNext();
            index++;
        }
    
        return -1; // Si no encontramos el elemento, devolvemos -1
    }
    


}











