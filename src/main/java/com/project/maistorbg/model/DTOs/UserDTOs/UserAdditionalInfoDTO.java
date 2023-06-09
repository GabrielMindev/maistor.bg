package com.project.maistorbg.model.DTOs.UserDTOs;

import com.project.maistorbg.model.DTOs.CommentDTOs.CommentSimpleDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostSimpleDTO;
import com.project.maistorbg.model.DTOs.RepairCategoryDTOs.AddRepairCategoryDTO;

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
    private List<AddRepairCategoryDTO> categories;

}
