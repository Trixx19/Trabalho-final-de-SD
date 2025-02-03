package com.aluguelcarros;

public class CarroNotExistsException extends Exception {
    public CarroNotExistsException(String message){
        super(message);
    }
}
