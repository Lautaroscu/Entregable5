package com.viajes.viajes.exceptions;

public class ScooterNotFoundException extends RuntimeException {
    public ScooterNotFoundException(String message) {
      super(message);
    }
}
