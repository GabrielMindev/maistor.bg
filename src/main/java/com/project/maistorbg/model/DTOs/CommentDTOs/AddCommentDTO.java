package com.project.maistorbg.model.DTOs.CommentDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCommentDTO {

    @NotNull(message = "Receiver id cannot be null")
    @Positive(message = "Receiver id must be a positive integer")
    private Integer receiverId;

    @NotBlank(message = "Comment text cannot be blank")
    @Size(max = 1000, message = "Comment text cannot be longer than 1000 characters")
    private String text;


}
