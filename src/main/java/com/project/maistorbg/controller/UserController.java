package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.RateDTOs.RateDTO;
import com.project.maistorbg.model.DTOs.RateDTOs.RateResponseDTO;
import com.project.maistorbg.model.DTOs.RepairCategoryDTOs.CategoryWithNameDTO;
import com.project.maistorbg.model.DTOs.UserDTOs.*;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import com.project.maistorbg.service.MediaService;
import com.project.maistorbg.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserWithoutPasswordDTO> register(@RequestBody UserRegisterDTO registerDTO, HttpServletRequest request) {
        UserWithoutPasswordDTO user = userService.register(registerDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserAdditionalInfoDTO> login(@RequestBody UserLoginDTO dto, HttpSession s) {
        UserAdditionalInfoDTO respDto = userService.login(dto);
        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", respDto.getId());
        return ResponseEntity.ok(respDto);
    }

    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("You are successfully logged out!");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserAdditionalInfoDTO> getById(@PathVariable int id) {
        UserAdditionalInfoDTO user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<UserAdditionalInfoDTO>> getAllUsers() {
        List<UserAdditionalInfoDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("users/categories/{categoryName}")
    public ResponseEntity<List<UserWithoutPasswordDTO>> getByCategory(@PathVariable CategoryWithNameDTO categoryName) {
        List<UserWithoutPasswordDTO> users = userService.getAllWorkmenForCategory(categoryName.getNameRepair());
        return ResponseEntity.ok(users);
    }

    @PutMapping("users")
    public ResponseEntity<UserEditDTO> editUser(@RequestBody UserEditDTO dto, HttpSession s) {
        if(!checkIfAuthorized(s)){
            throw new UnauthorizedException("You have to login");
        }
        UserEditDTO user = userService.editUser(dto, (Integer) s.getAttribute("LOGGED_ID"));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteProfile(HttpSession s) {
        if (!checkIfAuthorized(s)) {
            throw new UnauthorizedException("You have to login!");
        }
        userService.deleteProfile((Integer) s.getAttribute("LOGGED_ID"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/categories")
    public ResponseEntity<UserAdditionalInfoDTO> addCategory(@RequestBody CategoryWithNameDTO categoryName, HttpSession s) {
        if(!checkIfAuthorized(s)){
            throw new UnauthorizedException("You have to login!");
        }
        UserAdditionalInfoDTO user = userService.addCategory((Integer) s.getAttribute("LOGGED_ID"), categoryName.getNameRepair());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users/image")
    public ResponseEntity<UserSimpleDTO> uploadProfileImage(@RequestParam(name = "file") MultipartFile file, HttpSession s) {
        if(!checkIfAuthorized(s)){
            throw new UnauthorizedException("You have to login");
        }
        UserSimpleDTO user = mediaService.upload(file, (Integer)s.getAttribute("LOGGED_ID"));
        return ResponseEntity.ok(user);
    }

    @SneakyThrows
    @GetMapping("/users/media/{filename}")
    public ResponseEntity<Void> download(@PathVariable String filename, HttpServletResponse response) {
        File f = mediaService.download(filename);
        Files.copy(f.toPath(), response.getOutputStream());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/rating")
    public ResponseEntity<RateResponseDTO> rate(@RequestParam(name = "ratedId") int ratedId, @RequestBody RateDTO rating, HttpSession s) {
        if(!checkIfAuthorized(s)){
            throw new UnauthorizedException("You have to login!");
        }

        RateResponseDTO rateResponseDTO = userService.createRate((Integer) s.getAttribute("LOGGED_ID"), ratedId, rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(rateResponseDTO);
    }


    private boolean checkIfAuthorized(HttpSession s) {
        return s.getAttribute("LOGGED_ID") != null;
    }
}


