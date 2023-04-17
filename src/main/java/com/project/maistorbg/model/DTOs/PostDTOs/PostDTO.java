package com.project.maistorbg.model.DTOs.PostDTOs;


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

        private String categoryName;
        private String description;
        private String cityName;


}
