package com.pragma.powerup.usermicroservice.adapters.driving.http.exceptions;

public class MissingBirthdateValidationException extends IllegalArgumentException{
    public MissingBirthdateValidationException(String message){
        super(message);
    }
}
