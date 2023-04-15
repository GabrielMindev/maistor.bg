package com.project.maistorbg.model.DTOs.CommentDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCommentDTO {

    private String text;
    private int receiverId;
}
