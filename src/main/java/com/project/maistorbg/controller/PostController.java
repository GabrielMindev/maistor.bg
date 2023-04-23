package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.PostDTOs.PostDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostResponseDTO;
import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.repositories.ApplicationRepository;

import com.project.maistorbg.model.repositories.PostRepository;
import com.project.maistorbg.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class PostController extends AbstractController {

    @Autowired
    PostService postService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ApplicationRepository applicationRepository;


    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> addPost(HttpSession s, @RequestBody PostDTO postDTO) {
        int userId = (int) s.getAttribute("LOGGED_ID");
        PostResponseDTO post = postService.addPost(userId, postDTO);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> deletePost(@PathVariable int postId, HttpSession s) {
        return ResponseEntity.ok(postService.deletePost(postId, (int) s.getAttribute("LOGGED_ID")));
    }


    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDTO> editPost(@PathVariable int id, @RequestBody PostDTO postDTO, HttpSession s) {
        int userId = (int) s.getAttribute("LOGGED_ID");
        PostResponseDTO post = postService.editPost(postDTO, id, userId);
        return ResponseEntity.ok(post);
    }


    @GetMapping("/posts/user/{id}")
    public ResponseEntity<Page<PostResponseDTO>> getPostsForUser(@PathVariable int id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Page<PostResponseDTO> dto = postService.getAllPostForUser(id, page, size);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/posts")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Page<PostResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Page<PostResponseDTO> dto = postService.getAll(page, size);
        return ResponseEntity.ok(dto);
    }
}