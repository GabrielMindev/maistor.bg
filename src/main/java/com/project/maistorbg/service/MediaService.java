package com.project.maistorbg.service;

import com.project.maistorbg.model.DTOs.UserDTOs.UserSimpleDTO;
import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class MediaService extends AbstractService{

    @SneakyThrows
    public UserSimpleDTO upload(MultipartFile origin, int userId) {

        User u = getUserById(userId);
        String name = UUID.randomUUID().toString();
        String ext = FilenameUtils.getExtension(origin.getOriginalFilename());

        name = name + "." + ext;
        File dir = new File("uploads");
        if(!dir.exists()){
            dir.mkdirs();
        }
        File f = new File(dir, name);
        Files.copy(origin.getInputStream(), f.toPath());

        String path = dir.getName() + File.separator + f.getName();
        u.setProfilePhoto(path);
        userRepository.save(u);
        return mapper.map(u, UserSimpleDTO.class);
    }

    public File download(String fileName) {
        fileName = "uploads" + File.separator + fileName;
        File f = new File(fileName);
        if(f.exists()){
            return f;
        }
        throw new NotFoundException("File not found");
    }
}