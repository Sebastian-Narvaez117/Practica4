package com.practica5.controller.dao.implement;

import com.practica5.controller.tda.list.LinkedList;

public interface InterfazDao <T> {
    public void persist(T object) throws Exception;
    public void marge(T object, Integer index) throws Exception;
    public LinkedList<T> listAll();
    public T get(Integer id) throws Exception;
    
}
