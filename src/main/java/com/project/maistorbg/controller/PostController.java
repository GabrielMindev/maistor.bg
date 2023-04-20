package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.PostDTOs.PostDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostResponseDTO;
import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.repositories.ApplicationRepository;

import com.project.maistorbg.model.repositories.PostRepository;
import com.project.maistorbg.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<PostResponseDTO> addPost(HttpServletRequest request,@RequestBody PostDTO postDTO) {
        Integer userId = (Integer) request.getSession().getAttribute("LOGGED_ID");
        PostResponseDTO post = postService.addPost(userId, postDTO);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> deletePost(@PathVariable int postId,HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("LOGGED_ID");
        PostResponseDTO post = postService.deletePost(postId,userId);
        return ResponseEntity.ok(postService.deletePost(postId, (Integer) request.getSession().getAttribute("LOGGED_ID")));

    }


    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDTO> editPost(@PathVariable int id, @RequestBody PostDTO postDTO,HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("LOGGED_ID");
        PostResponseDTO post = postService.editPost(postDTO, id,userId);
        return ResponseEntity.ok(post);
    }



    @GetMapping("users/{id}/posts")
    public ResponseEntity<List<PostResponseDTO>> getPostsForUser(@PathVariable int id, HttpServletRequest request) {
        List<Post> posts = postService.getAllPostForUser(id);
        List<PostResponseDTO> dto = mapper.map(posts, new TypeToken<List<PostResponseDTO>>() {
        }.getType());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/posts/all")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PostResponseDTO> getAll(){
        return postService.getAll();
    }


}