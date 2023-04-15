package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.UserAdditionalInfoDTO;
import com.project.maistorbg.model.DTOs.UserLoginDTO;
import com.project.maistorbg.model.DTOs.UserRegisterDTO;
import com.project.maistorbg.model.DTOs.UserWithoutPasswordDTO;
import com.project.maistorbg.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController extends MyExceptionHandler {

    @Autowired
    UserService userService;

    @PostMapping("/users/register")
    public UserWithoutPasswordDTO register(@RequestBody UserRegisterDTO registerDTO, HttpServletRequest request) {
        return userService.register(registerDTO);
    }


    @PostMapping("/users/login")
    public UserAdditionalInfoDTO login(@RequestBody UserLoginDTO dto, HttpSession s) {
        UserAdditionalInfoDTO respDto = userService.login(dto);
        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", respDto.getId());
        return respDto;

    }

    @GetMapping("/users/{id}")
    public UserAdditionalInfoDTO getById(@PathVariable int id){
        return userService.getById(id);
    }


}

