package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.ErrorDTO;
import com.project.maistorbg.model.exceptions.BadRequestException;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

public abstract class AbstractController {

    @ExceptionHandler(value=BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Exception e) {
        return generateErrorDTO(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value=UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(Exception e) {
        return generateErrorDTO(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value=NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception e) {
        return generateErrorDTO(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value=Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleRest(Exception e) {
        return generateErrorDTO(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    private ErrorDTO generateErrorDTO(Exception e, HttpStatus s) {
//        return ErrorDTO.builder()
//                .msg(e.getMessage())
//                .time(LocalDateTime.now())
//                .status(s.value())
//                .build();
//    }
    private ErrorDTO generateErrorDTO(Exception exception, HttpStatus status){
        ErrorDTO exceptionDTO = new ErrorDTO();
        exceptionDTO.setTime(LocalDateTime.now());
        exceptionDTO.setMsg(exception.getMessage());
        exceptionDTO.setStatus(status.value());
        return exceptionDTO;
    }
}