package com.project.maistorbg.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class CommentSimpleDTO {
    private int id;
    private String text;
    private LocalDate date;
}
