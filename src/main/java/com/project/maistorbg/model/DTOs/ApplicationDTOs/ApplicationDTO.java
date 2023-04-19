package com.project.maistorbg.model.DTOs.ApplicationDTOs;

import com.project.maistorbg.model.DTOs.PostDTOs.PostForApplicationResponseDTO;
import com.project.maistorbg.model.DTOs.UserWithoutPasswordDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private int id;
    private double price_per_service;
    private int daysNeeded;
    private PostForApplicationResponseDTO post;
    private UserWithoutPasswordDTO user;

}
