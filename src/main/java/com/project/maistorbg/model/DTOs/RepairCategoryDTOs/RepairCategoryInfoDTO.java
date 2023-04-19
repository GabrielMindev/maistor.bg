package com.project.maistorbg.model.DTOs.RepairCategoryDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class RepairCategoryInfoDTO {

    private int id;
    private String nameRepair;
    private LocalDate date = LocalDate.now();
}
