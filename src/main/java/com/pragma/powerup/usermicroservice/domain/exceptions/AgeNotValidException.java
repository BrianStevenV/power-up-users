package com.pragma.powerup.usermicroservice.domain.exceptions;

public class AgeNotValidException extends RuntimeException{
    public AgeNotValidException(String message){
        super(message);
    }
}
