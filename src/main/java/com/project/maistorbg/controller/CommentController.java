package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.CommentDTOs.AddCommentDTO;
import com.project.maistorbg.model.DTOs.CommentDTOs.CommentInfoDTO;
import com.project.maistorbg.model.DTOs.CommentDTOs.EditCommentDTO;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import com.project.maistorbg.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController extends MyExceptionHandler{
    @Autowired
    CommentService commentService;

    @GetMapping("/comments/{sender_id}")
    public List<CommentInfoDTO> getAllCommentsBySender(HttpSession session) {
        if (session.getAttribute("LOGGED_ID") == null) {
            throw new UnauthorizedException("You have to login!");
        }
        int senderId = (int) session.getAttribute("LOGGED_ID");
        return commentService.getAllBySenderId(senderId);
    }

    @PostMapping("/comments")
    public CommentInfoDTO addComment (@RequestBody AddCommentDTO dto, HttpSession s){
        if(s.getAttribute("LOGGED_ID") == null){
            throw new UnauthorizedException("You have to login!");
        }
        int id = (int) s.getAttribute("LOGGED_ID");
        return commentService.add(dto,id);
    }

    @PutMapping("/comments/{commentId}")
    public CommentInfoDTO editComment(@PathVariable int commentId, @RequestBody EditCommentDTO dto, HttpSession s) {
        if (s.getAttribute("LOGGED_ID") == null) {
            throw new UnauthorizedException("You have to login!");
        }
        int senderId = (int) s.getAttribute("LOGGED_ID");
        return commentService.edit(commentId, dto, senderId);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteCommentById(@PathVariable int commentId, HttpSession s) {
        if(s.getAttribute("LOGGED_ID") == null){
            throw new UnauthorizedException("You have to login!");
        }
        int senderId = (int) s.getAttribute("LOGGED_ID");
        commentService.delete(commentId, senderId);
    }
}
