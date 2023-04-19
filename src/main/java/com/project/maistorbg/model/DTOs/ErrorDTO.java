package com.project.maistorbg.model.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO {

    private String msg;
    private int status;
    private LocalDateTime time;
}

