package com.project.maistorbg.service;

import com.project.maistorbg.model.DTOs.CommentDTOs.AddCommentDTO;
import com.project.maistorbg.model.DTOs.CommentDTOs.CommentInfoDTO;
import com.project.maistorbg.model.DTOs.CommentDTOs.EditCommentDTO;
import com.project.maistorbg.model.entities.Comment;
import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService extends AbstractService{

    public List<CommentInfoDTO> getAllBySenderId(int senderId) {
        List<Comment> comments = commentRepository.findAllBySenderId(senderId);
        return comments.stream()
                .map(comment -> mapper.map(comment, CommentInfoDTO.class))
                .collect(Collectors.toList());
    }

    public CommentInfoDTO add(AddCommentDTO dto, int senderId) {
        Comment comment = mapper.map(dto, Comment.class);
        User user = userRepository.findById(senderId).orElseThrow(() -> new NotFoundException("User not found!"));
        comment.setSender(user);
        commentRepository.save(comment);
        return mapper.map(comment, CommentInfoDTO.class);
    }

    public CommentInfoDTO edit(int commentId, EditCommentDTO dto, int senderId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("The resource has not been found!"));
        if (comment.getSender().getId() != senderId) {
            throw new UnauthorizedException("You are not authorized to edit this comment.");
        }
        comment.setText(dto.getText());
        comment.setDate(LocalDateTime.now());
        comment.setReceiver(userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new NotFoundException("The resource has not been found!")));
        Comment updatedComment = commentRepository.save(comment);
        return mapper.map(updatedComment, CommentInfoDTO.class);
    }

    public void delete(int commentId, int senderId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found!"));
        if (comment.getSender().getId() != senderId) {
            throw new UnauthorizedException("You are not authorized to delete this comment!");
        }
        commentRepository.delete(comment);
    }
}
