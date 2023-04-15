package com.project.maistorbg.model.DTOs;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ErrorDTO {

    private String msg;
    private int status;
    private LocalDateTime time;
}

