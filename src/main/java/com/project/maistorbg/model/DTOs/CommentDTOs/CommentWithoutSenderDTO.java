package com.project.maistorbg.model.DTOs.CommentDTOs;

import com.project.maistorbg.model.DTOs.UserWithoutPasswordDTO;

public class CommentWithoutSenderDTO {
    private int id;
    private String text;
    private UserWithoutPasswordDTO receiver;
}
