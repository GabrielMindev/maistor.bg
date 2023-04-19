package com.project.maistorbg.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditDTO {
    private String username;
    private int age;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String roleName;
    private String profilePhotoUrl;
}
