package com.project.maistorbg.model.DTOs.ApplicationDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCreateDTO {
    private int postId;
    private double price_per_service;
    private int daysNeeded;

}
