package com.project.maistorbg.model.DTOs.CommentDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditCommentDTO {

    @NotBlank(message = "Comment text cannot be blank")
    @Size(max = 1000, message = "Comment text cannot be longer than 1000 characters")
    private String text;

}
