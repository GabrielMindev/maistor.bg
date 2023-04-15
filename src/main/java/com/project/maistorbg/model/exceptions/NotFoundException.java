package com.project.maistorbg.model.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg){
        super(msg);
    }
}