package com.project.maistorbg.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UserAdditionalInfoDTO extends UserSimpleDTO {
    private List<PostSimpleDTO> posts;
    private List<CommentSimpleDTO> comments;

}
