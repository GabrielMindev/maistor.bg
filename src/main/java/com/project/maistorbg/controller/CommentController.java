package com.project.maistorbg.controller;
import com.project.maistorbg.model.DTOs.CommentDTOs.AddCommentDTO;
import com.project.maistorbg.model.DTOs.CommentDTOs.CommentInfoDTO;
import com.project.maistorbg.model.DTOs.CommentDTOs.EditCommentDTO;
import com.project.maistorbg.model.DTOs.RepairCategoryDTOs.RepairCategoryInfoDTO;
import com.project.maistorbg.model.entities.RepairCategory;
import com.project.maistorbg.service.CommentService;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CommentController extends AbstractController{
    @Autowired
    CommentService commentService;

    @GetMapping("users/comments")
    public ResponseEntity<List<CommentInfoDTO>> getAllCommentsBySender(HttpSession s ) {
        if (!checkIfAuthorized(s)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        int senderId = (int) s.getAttribute("LOGGED_ID");
        List<CommentInfoDTO> comments = commentService.getAllBySenderId(senderId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentInfoDTO> addComment ( @RequestBody AddCommentDTO dto,HttpSession s){
        if (!checkIfAuthorized(s)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        int id = (int) s.getAttribute("LOGGED_ID");
        CommentInfoDTO addedComment = commentService.add(dto,id, dto.getReceiverId());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedComment);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentInfoDTO> editComment(@PathVariable int commentId, @RequestBody EditCommentDTO dto, HttpSession s) {
        if (!checkIfAuthorized(s)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        int senderId = (int) s.getAttribute("LOGGED_ID");
        CommentInfoDTO editedComment = commentService.edit(commentId, dto, senderId);
        return ResponseEntity.ok(editedComment);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable int commentId, HttpSession s) {
        if (!checkIfAuthorized(s)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        int senderId = (int) s.getAttribute("LOGGED_ID");
        commentService.delete(commentId, senderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    private boolean checkIfAuthorized(HttpSession s) {
        return s.getAttribute("LOGGED_ID") != null;
    }
}
