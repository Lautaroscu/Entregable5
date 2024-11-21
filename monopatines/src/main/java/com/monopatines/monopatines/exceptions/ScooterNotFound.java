package com.monopatines.monopatines.exceptions;

public class ScooterNotFound extends RuntimeException {
    public ScooterNotFound(String msg) {
        super(msg);
    }
}
