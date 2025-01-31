package com.practica5.controller.dao.services;

import com.practica5.controller.dao.CiudadDao;
import com.practica5.controller.tda.list.LinkedList;
import com.practica5.controller.tda.models.City;

public class CiudadServices {
    private CiudadDao obj;

    public CiudadServices(){
        obj = new CiudadDao();
    }

    public Boolean save() throws Exception{
        return obj.save();
    }

    public Boolean update() throws Exception{
        return obj.update();
    }


    public LinkedList<City> listAll() {
        return obj.getListAll();
    }

    public City getCiudad(){
        return obj.getCiudad();
    }

    public void setCiudad(City ciudad){
        obj.setCiudad(ciudad);
    }


    public City get(Integer id) throws Exception {
        return obj.get(id);
    }
    
    public Boolean delete(int id) throws Exception {
        return obj.delete(id);
    }
    
}
