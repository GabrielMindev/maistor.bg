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
public class ApplicationService extends AbstractService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ApplicationRepository applicationRepository;

    public List<ApplicationDTO> getAllApplicationPerPost(int postId, int userId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new NotFoundException("Post not found"));
        if (post.getOwner().getId() != userId){
            throw new UnauthorizedException("User is not post owner");
        }
        return applicationRepository.findAllByPost(post).stream().map(post2 -> modelMapper.map(post2,ApplicationDTO.class)).collect(Collectors.toList());
    }

    public ApplicationDTO addApplication(ApplicationCreateDTO createApplication, int id) {

        Post post = postRepository.findById(createApplication.getPostId()).orElseThrow(() -> new BadRequestException("Post not found"));
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));

         if (!user.getRoleName().equals("workman")){
            throw new BadRequestException("User is not workman");
         }
        if (post.getOwner() == user) {
            throw new BadRequestException("User is post owner");
        }
        Application application = new Application();
        application.setDaysNeeded(createApplication.getDaysNeeded());
        application.setPrice_per_service(createApplication.getPrice_per_service());
        application.setPost(post);
        application.setUser(user);
        application = applicationRepository.save(application);
        return modelMapper.map(application, ApplicationDTO.class);
    }

    public ApplicationDTO editApplication(ApplicationDTO offerEditDTO, int id){
        Application application = applicationRepository.findById(offerEditDTO.getId()).orElseThrow(()-> new NotFoundException("Application not found"));
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
        if (application.getUser() != user){
            throw new UnauthorizedException("User isn't offer owner");
        }
        if (application.getPrice_per_service()<0){
            throw new BadRequestException("Invalid price");
        }
        application.setDaysNeeded(offerEditDTO.getDaysNeeded());
        application.setPrice_per_service(offerEditDTO.getPrice_per_service());
        application.setDaysNeeded(offerEditDTO.getDaysNeeded());
        application = applicationRepository.save(application);
        return modelMapper.map(application,ApplicationDTO.class);

    }
    public ApplicationDTO deleteById(int applicationId,int userId){
        Application application = applicationRepository.findById(applicationId).orElseThrow(()->new NotFoundException("Application not found"));

        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found"));
        if (application.getUser() != user){
            throw new UnauthorizedException("User is not offer owner");
        }

        applicationRepository.deleteById(applicationId);
        return modelMapper.map(application,ApplicationDTO.class);
    }

}
