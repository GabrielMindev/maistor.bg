package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.*;
import com.project.maistorbg.model.DTOs.UserDTOs.*;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import com.project.maistorbg.service.MediaService;
import com.project.maistorbg.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
public class UserController extends AbstractController {

    @Autowired
    UserService userService;

    @Autowired
    MediaService mediaService;


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
        return userService.getAllWorkmenForCategory(categoryName.getNameRepair());
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
            return userService.addCategory((Integer) request.getSession().getAttribute("LOGGED_ID"), categoryName.getNameRepair());
        } else {
            throw new UnauthorizedException("You have to login!");
        }
    }

    @PostMapping("/users/image")
    public UserSimpleDTO uploadProfileImage(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("LOGGED") != null) {
            return mediaService.upload(file,(Integer) request.getSession().getAttribute("LOGGED_ID"));
        } else {
            throw new UnauthorizedException("You have to login!");
        }
    }

    @SneakyThrows
    @GetMapping("/users/media/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse response){
      File f= mediaService.download(filename);
        Files.copy(f.toPath(), response.getOutputStream());
    }

    @PostMapping("/users/rating")
    public RateResponseDTO rate(@RequestParam(name = "ratedId") int ratedId,  @RequestBody RateDTO rating, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("LOGGED") != null) {
            return userService.createRate((Integer) request.getSession().getAttribute("LOGGED_ID"),  ratedId,rating);
        } else {
            throw new UnauthorizedException("You have to login!");
        }
    }
}


