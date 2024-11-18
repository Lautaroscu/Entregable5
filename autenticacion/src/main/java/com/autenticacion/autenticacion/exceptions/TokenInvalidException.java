package com.autenticacion.autenticacion.exceptions;

public class TokenInvalidException extends RuntimeException{
    public TokenInvalidException(String message){
        super(message);
    }
}
