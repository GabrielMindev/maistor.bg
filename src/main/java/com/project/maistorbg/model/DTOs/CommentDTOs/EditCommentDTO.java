package com.project.maistorbg.model.DTOs.CommentDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditCommentDTO {

    private int id;
    private int receiverId;
    private String text;

}
