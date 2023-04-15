package com.project.maistorbg.model.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String msg){
        super(msg);
    }
}