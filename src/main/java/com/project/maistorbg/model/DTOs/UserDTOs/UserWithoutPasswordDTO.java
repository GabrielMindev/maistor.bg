package com.project.maistorbg.model.DTOs.UserDTOs;

import com.project.maistorbg.model.DTOs.CommentDTOs.CommentWithoutSenderDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserWithoutPasswordDTO {
    private int id;
    private String username;
    private int age;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String roleName;
    private String profilePhotoUrl;
    private List<CommentWithoutSenderDTO> comments;
}
