package com.project.maistorbg.service;

import com.project.maistorbg.model.DTOs.*;
import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.BadRequestException;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import com.project.maistorbg.util.UtilityUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService{
    @Autowired
    private BCryptPasswordEncoder encoder;
    public UserWithoutPasswordDTO register(UserRegisterDTO registerDTO) {

        if (!registerDTO.getFirstName().matches(UtilityUser.nameRegex)) {
            throw new BadRequestException("Invalid first name. You should use only letters.");
        } else {
            // The name is valid, but if the first letter is not capitalized it will be
            String first_name = registerDTO.getFirstName().substring(0, 1).toUpperCase() + registerDTO.getFirstName().substring(1);
            registerDTO.setFirstName(first_name);

        }

        if (!registerDTO.getLastName().matches(UtilityUser.nameRegex)) {
            throw new BadRequestException("Invalid last name. You should use only letters.");
        } else {
            // The name is valid, but if the first letter is not capitalized it will be
            String last_name = registerDTO.getLastName().substring(0, 1).toUpperCase() + registerDTO.getLastName().substring(1);
            registerDTO.setLastName(last_name);
        }


        if (!UtilityUser.isPasswordMatch(registerDTO)){
            throw new BadRequestException("Password mismatch");
        }
        if (!UtilityUser.isStrongPassword(registerDTO.getPassword())){
            throw new BadRequestException("Password doesn't match the requirements");
        }
        if (!UtilityUser.isEmailValid(registerDTO.getEmail())){
            throw new BadRequestException("Invalid email");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())){
            throw  new BadRequestException("User already exists");
        }
        String phoneNumber = UtilityUser.validateAndRestyleNumber(registerDTO.getPhoneNumber());
        if (userRepository.findUserByPhoneNumber(phoneNumber).isPresent()){
            throw new BadRequestException("User with the same phone number already exists!");
        }
        User u = mapper.map(registerDTO,User.class);
        u.setPhoneNumber(phoneNumber);
        u.setRegistrationDate(LocalDate.now());
        u.setPassword(encoder.encode(registerDTO.getPassword()));
        u=userRepository.save(u);

        return mapper.map(u,UserWithoutPasswordDTO.class);
    }

    public UserAdditionalInfoDTO login(UserLoginDTO dto){
        Optional<User> u = userRepository.findUsersByEmail(dto.getEmail());
        if(!u.isPresent()){
            throw new UnauthorizedException("Wrong credentials");
        }
        if(!encoder.matches(dto.getPassword(), u.get().getPassword())){
            throw new UnauthorizedException("Wrong credentials");
        }
        return mapper.map(u, UserAdditionalInfoDTO.class);
    }

    public UserAdditionalInfoDTO getById(int id) {
        Optional<User> u = userRepository.findById(id);
        if(u.isPresent()){
            return mapper.map(u.get(), UserAdditionalInfoDTO.class);
        }
        throw new NotFoundException("User not found");
    }


    public List<UserAdditionalInfoDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u->mapper.map(u, UserAdditionalInfoDTO.class))
                .collect(Collectors.toList());
    }

//    public List<UserWithoutPasswordDTO> getAllUsersByCategory(String categoryName) {
//        //return getAllUsersByCategory(categoryName);
//        return userRepository.findAllByCategory(categoryName);
//    }


    public UserEditDTO editUser(UserEditDTO dto, int id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
        if (!dto.getFirstName().matches(UtilityUser.nameRegex)) {
            throw new BadRequestException("Invalid first name. You should use only letters.");
        }

        if (!dto.getLastName().matches(UtilityUser.nameRegex)) {
            throw new BadRequestException("Invalid last name. You should use only letters.");
        }
        String phoneNumber = UtilityUser.validateAndRestyleNumber(dto.getPhoneNumber());
        if (userRepository.findUserByPhoneNumber(phoneNumber).isPresent()){
            throw new BadRequestException("User with the same phone number already exists!");
        }
        if (!UtilityUser.isEmailValid(dto.getEmail())){
            throw new BadRequestException("Invalid email");
        }

        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAge(dto.getAge());
        user.setRoleName(dto.getRoleName());
        user.setPhoneNumber(phoneNumber);
        user.setProfilePhoto(dto.getProfilePhotoUrl());
        user.setEmail(dto.getEmail());
        user = userRepository.save(user);
        return mapper.map(user,UserEditDTO.class);
    }

    public void deleteProfile(int id) {
      userRepository.deleteById(id);
    }
}
