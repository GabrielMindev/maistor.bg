package com.project.maistorbg.model.DTOs.ApplicationDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationForPostResponseDTO {
    private int id;
    private double price_per_service;
    private int daysNeeded;
}
