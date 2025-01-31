package com.practica5.controller.exception;

public class ListEmptyException extends Exception {


    public ListEmptyException(){}
    
    public ListEmptyException(String message) {
        super(message);
    }
}
