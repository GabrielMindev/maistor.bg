package com.project.maistorbg.service;

import com.project.maistorbg.model.DTOs.ApplicationDTOs.ApplicationCreateDTO;
import com.project.maistorbg.model.DTOs.ApplicationDTOs.ApplicationDTO;
import com.project.maistorbg.model.entities.Application;
import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.BadRequestException;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import com.project.maistorbg.model.repositories.ApplicationRepository;
import com.project.maistorbg.model.repositories.PostRepository;
import com.project.maistorbg.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ApplicationService extends AbstractService {


    public List<ApplicationDTO> getAllApplicationPerPost(int postId, int userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        if (post.getOwner().getId() != userId) {
            throw new UnauthorizedException("User is not post owner");
        }
        return applicationRepository.findAllByPost(post).stream().map(post2 -> mapper.map(post2, ApplicationDTO.class)).collect(Collectors.toList());
    }

    public ApplicationDTO addApplication(ApplicationCreateDTO createApplication, int id) {

        Post post = postRepository.findById(createApplication.getPostId()).orElseThrow(() -> new BadRequestException("Post not found"));
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));

        if (!user.getRoleName().equals("workman")) {
            throw new BadRequestException("User is not workman");
        }
        if (post.getOwner().getId() == (user.getId())) {
            throw new BadRequestException("User is post owner");
        }

        boolean exists = applicationRepository.existsByPostIdAndUserId(post.getId(), id);
        if (exists){
            throw new BadRequestException("application already exists for this user");
        }

        if (createApplication.getPrice_per_service() <= 0 || createApplication.getDaysNeeded() <= 0) {
            throw new BadRequestException("Invalid price or invalid given days");
        }

        Application application = new Application();
        application.setDaysNeeded(createApplication.getDaysNeeded());
        application.setPrice_per_service(createApplication.getPrice_per_service());
        application.setPost(post);
        application.setUser(user);

        application = applicationRepository.save(application);
        return mapper.map(application, ApplicationDTO.class);


    }

    public ApplicationDTO editApplication(ApplicationDTO applicationDTO, int id) {
        Application application = applicationRepository.findById(applicationDTO.getId()).orElseThrow(() -> new NotFoundException("Application not found"));
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        if (application.getUser().getId() != user.getId()) {
            throw new UnauthorizedException("User isn't application owner");
        }
        if (application.getPrice_per_service() <= 0 || application.getDaysNeeded() <= 0) {
            throw new BadRequestException("Invalid price or invalid given days");
        }
        application.setDaysNeeded(applicationDTO.getDaysNeeded());
        application.setPrice_per_service(applicationDTO.getPrice_per_service());
        application.setDaysNeeded(applicationDTO.getDaysNeeded());
        application = applicationRepository.save(application);
        return mapper.map(application, ApplicationDTO.class);

    }

    public ApplicationDTO deleteById(int applicationId, int userId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new NotFoundException("Application not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        if (application.getUser().getId() != user.getId()) {
            throw new UnauthorizedException("User is not application owner");
        }

        applicationRepository.deleteById(applicationId);
        return mapper.map(application, ApplicationDTO.class);
    }

}
