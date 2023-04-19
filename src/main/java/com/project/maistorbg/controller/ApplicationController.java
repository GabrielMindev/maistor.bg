package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.ApplicationDTOs.ApplicationCreateDTO;
import com.project.maistorbg.model.DTOs.ApplicationDTOs.ApplicationDTO;
import com.project.maistorbg.model.entities.Application;
import com.project.maistorbg.model.repositories.ApplicationRepository;
import com.project.maistorbg.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
public class ApplicationController extends AbstractController{

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository appRepo;

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

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsPerPost(@PathVariable int postId, @RequestParam int userId) {
        List<ApplicationDTO> applications = applicationService.getAllApplicationPerPost(postId, userId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApplicationDTO> deleteApplication(@PathVariable int id, @RequestParam int userId) {
        ApplicationDTO deletedApplication = applicationService.deleteById(id, userId);
        return new ResponseEntity<>(deletedApplication, HttpStatus.OK);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<Application> acceptApplication(@PathVariable int id) {
        Optional<Application> optionalApplication = appRepo.findById(id);

        if (optionalApplication.isPresent()) {

            Application application = optionalApplication.get();
            application.setStatus(1);
            Application app = appRepo.save(application);
            return ResponseEntity.ok(app);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}