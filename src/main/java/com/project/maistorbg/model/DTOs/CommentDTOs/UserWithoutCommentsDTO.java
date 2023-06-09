package com.project.maistorbg.model.DTOs.CommentDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserWithoutCommentsDTO {

    private int id;
    private String username;
    private int age;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String roleName;
    private String profilePhoto;
}
