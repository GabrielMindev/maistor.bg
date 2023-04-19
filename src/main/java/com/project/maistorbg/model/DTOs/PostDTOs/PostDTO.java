package com.project.maistorbg.model.DTOs.PostDTOs;


import com.project.maistorbg.model.entities.RepairCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Component
    public class PostDTO {

        private String name;
        private String description;
        private String city;


}
