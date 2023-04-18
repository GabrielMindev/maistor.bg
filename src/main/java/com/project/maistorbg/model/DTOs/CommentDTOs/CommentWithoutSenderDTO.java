package com.project.maistorbg.model.DTOs.CommentDTOs;

import com.project.maistorbg.model.DTOs.UserWithoutPasswordDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CommentWithoutSenderDTO {

    private int id;
    private String text;
    private LocalDate date;
    private UserWithoutPasswordDTO receiver;
}
