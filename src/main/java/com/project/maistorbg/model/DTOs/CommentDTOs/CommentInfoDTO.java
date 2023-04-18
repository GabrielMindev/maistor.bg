package com.project.maistorbg.model.DTOs.CommentDTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CommentInfoDTO {

    private int id;
    private String text;
    private LocalDate date;
    private UserWithoutCommentsDTO receiver;
    private UserWithoutCommentsDTO sender;

}
