package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.*;
import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import com.project.maistorbg.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/users/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "You are successfully logout!";
    }

    @GetMapping("/users/{id}")
    public UserAdditionalInfoDTO getById(@PathVariable int id) {
        return userService.getById(id);
    }

    @GetMapping("/users/all")
    public List<UserAdditionalInfoDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("users/categories/{categoryName}")
    public List<UserWithoutPasswordDTO> getByCategory(@PathVariable CategoryWithNameDTO categoryName) {
        return userService.getAllWorkmenForCategory(categoryName.getCategoryName());
    }

    @PutMapping("users")
    public UserEditDTO editUser(@RequestBody UserEditDTO dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("LOGGED") != null) {
            return userService.editUser(dto, (Integer) request.getSession().getAttribute("LOGGED_ID"));
        } else {
            throw new UnauthorizedException("You have to login!");
        }
    }


    @DeleteMapping("/users")
    public void deleteProfile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("LOGGED") != null) {
            userService.deleteProfile((Integer) request.getSession().getAttribute("LOGGED_ID"));
        } else {
            throw new UnauthorizedException("You have to login!");
        }
    }

    @PostMapping("/users/categories")
    public UserAdditionalInfoDTO addCategory(@RequestBody CategoryWithNameDTO categoryName, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("LOGGED") != null) {
            return userService.addCategory((Integer) request.getSession().getAttribute("LOGGED_ID"), categoryName.getCategoryName());
        } else {
            throw new UnauthorizedException("You have to login!");
        }
    }
}


