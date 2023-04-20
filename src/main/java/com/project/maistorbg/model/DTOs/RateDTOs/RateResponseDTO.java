package com.project.maistorbg.model.DTOs.RateDTOs;

import com.project.maistorbg.model.DTOs.UserDTOs.UserWithoutPasswordDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class RateResponseDTO {
    private int id;
    private UserWithoutPasswordDTO user;
    private UserWithoutPasswordDTO ratedWorkman;
    private double rating;
}