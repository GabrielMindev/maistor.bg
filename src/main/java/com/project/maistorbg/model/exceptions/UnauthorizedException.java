package com.project.maistorbg.model.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String msg){
        super(msg);
    }
}