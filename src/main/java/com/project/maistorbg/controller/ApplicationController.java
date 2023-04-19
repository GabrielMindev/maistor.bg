package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.ApplicationDTOs.ApplicationCreateDTO;
import com.project.maistorbg.model.DTOs.ApplicationDTOs.ApplicationDTO;
import com.project.maistorbg.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationDTO> addApplication(@RequestBody ApplicationCreateDTO createApplication, @RequestParam int userId) {
        ApplicationDTO applicationDTO = applicationService.addApplication(createApplication, userId);
        return new ResponseEntity<>(applicationDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDTO> editApplication(@PathVariable int id, @RequestBody ApplicationDTO applicationDTO, @RequestParam int userId) {
        applicationDTO.setId(id);
        ApplicationDTO updatedApplication = applicationService.editApplication(applicationDTO, userId);
        return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsPerPost(@PathVariable int postId, @RequestParam int userId) {
        List<ApplicationDTO> applications = applicationService.getAllApplicationPerPost(postId, userId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApplicationDTO> deleteApplication(@PathVariable int id, @RequestParam int userId) {
        ApplicationDTO deletedApplication = applicationService.deleteById(id, userId);
        return new ResponseEntity<>(deletedApplication, HttpStatus.OK);
    }
}