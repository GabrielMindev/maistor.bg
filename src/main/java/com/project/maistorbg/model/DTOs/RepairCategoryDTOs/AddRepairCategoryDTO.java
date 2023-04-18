package com.project.maistorbg.model.DTOs.RepairCategoryDTOs;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddRepairCategoryDTO {

        @NotBlank(message = "Category name is mandatory")
        @Size(max = 45, message = "Category name cannot be longer than 45 characters")
        private String nameRepair;
}
