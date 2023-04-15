package com.project.maistorbg.model.exceptions;

public class ForbiddenException extends RuntimeException{

    public ForbiddenException(String msg){
        super(msg);
    }
}