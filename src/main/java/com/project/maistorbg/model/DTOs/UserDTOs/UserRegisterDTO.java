package com.project.maistorbg.model.DTOs.UserDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDTO {
    private String username;
    private int age;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String roleName;
    private String profilePhotoUrl;
}
