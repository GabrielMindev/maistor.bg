package com.project.maistorbg.model.DTOs.CommentDTOs;

import com.project.maistorbg.model.DTOs.UserDTOs.UserWithoutPasswordDTO;

public class CommentWithoutSenderDTO {
    private int id;
    private String text;
    private UserWithoutPasswordDTO receiver;
}
