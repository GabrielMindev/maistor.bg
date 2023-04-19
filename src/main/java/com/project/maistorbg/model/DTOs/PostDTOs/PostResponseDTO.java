package com.project.maistorbg.model.DTOs.PostDTOs;


import com.project.maistorbg.model.DTOs.UserWithoutPasswordDTO;
import com.project.maistorbg.model.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PostResponseDTO {

    private int id;
    private String description;
    private String cityName;
    private UserWithoutPasswordDTO owner;
    private LocalDate publication_date;

    public PostResponseDTO(Post post) {

    }

    public PostResponseDTO(PostResponseDTO post) {

    }

    //  public PostResponseDTO(PostResponseDTO post) {
  //  }
    //private Boolean isActive;

}