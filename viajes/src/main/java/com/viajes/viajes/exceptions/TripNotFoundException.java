package com.viajes.viajes.exceptions;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(String msj){
        super(msj);
    }
}
