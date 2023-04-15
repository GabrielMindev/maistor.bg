package com.project.maistorbg.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserSimpleDTO {

    private int id;
    private String email;
    private int age;
    private String profileImageUrl;
}