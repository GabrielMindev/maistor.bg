package com.project.maistorbg.model.DTOs.PostDTOs;

import com.project.maistorbg.model.DTOs.UserDTOs.UserWithoutPasswordDTO;
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
public class PostForApplicationResponseDTO {
    private int id;
    private String description;
    private String cityName;
    private UserWithoutPasswordDTO owner;
    private LocalDateTime publication_date;
}
