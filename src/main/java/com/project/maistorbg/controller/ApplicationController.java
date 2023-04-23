package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.ApplicationDTOs.ApplicationCreateDTO;
import com.project.maistorbg.model.DTOs.ApplicationDTOs.ApplicationDTO;
import com.project.maistorbg.model.entities.Application;
import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import com.project.maistorbg.model.repositories.ApplicationRepository;
import com.project.maistorbg.model.repositories.PostRepository;
import com.project.maistorbg.service.ApplicationService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
public class ApplicationController extends AbstractController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ApplicationRepository appRepo;

    @Autowired
    ModelMapper mapper;

    @PostMapping
    public ResponseEntity<ApplicationDTO> addApplication(@RequestBody ApplicationCreateDTO createApplication, HttpSession s) {
        ApplicationDTO applicationDTO = applicationService.addApplication(createApplication, (int) s.getAttribute("LOGGED_ID"));
        return new ResponseEntity<>(applicationDTO, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDTO> editApplication(@PathVariable int id, @RequestBody ApplicationDTO applicationDTO, HttpSession s) {
        applicationDTO.setId(id);
        ApplicationDTO updatedApplication = applicationService.editApplication(applicationDTO, (int) s.getAttribute("LOGGED_ID"));
        return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsPerPost(@PathVariable int postId, HttpSession s) {
        List<ApplicationDTO> applications = applicationService.getAllApplicationPerPost(postId, (int) s.getAttribute("LOGGED_ID"));
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApplicationDTO> deleteApplication(@PathVariable int id, HttpSession s) {
        ApplicationDTO deletedApplication = applicationService.deleteById(id, (int) s.getAttribute("LOGGED_ID"));
        return new ResponseEntity<>(deletedApplication, HttpStatus.OK);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<ApplicationCreateDTO> acceptApplication(@PathVariable int id, HttpSession s) {
        Application application = appRepo.findById(id).orElseThrow(() -> new NotFoundException("Application not found!"));
        Post post = application.getPost();
        User user = postRepository.findById(post.getId()).get().getOwner();
        if ((int) s.getAttribute("LOGGED_ID") != user.getId()) {
            throw new UnauthorizedException("You can't accept this application due to missing authorization rights.");
        }
        application.setStatus(1);
        Application app = appRepo.save(application);
        ApplicationCreateDTO applicationDTO = mapper.map(app, ApplicationCreateDTO.class);
        return ResponseEntity.ok(applicationDTO);
    }
}
