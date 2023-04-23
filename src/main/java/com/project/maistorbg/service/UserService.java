package com.project.maistorbg.service;

import com.project.maistorbg.model.DTOs.RateDTOs.RateDTO;
import com.project.maistorbg.model.DTOs.RateDTOs.RateResponseDTO;
import com.project.maistorbg.model.DTOs.UserDTOs.*;
import com.project.maistorbg.model.entities.Rating;
import com.project.maistorbg.model.entities.RepairCategory;
import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.BadRequestException;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import com.project.maistorbg.util.UtilityUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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
        if(registerDTO.getAge()<0){
            throw new BadRequestException("Invalid age given!");
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
        if(!UtilityUser.isValidRole(registerDTO.getRoleName())){
            throw new BadRequestException("Invalid role name");
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
            throw new UnauthorizedException("Wrong credentials!");
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

    public List<UserWithoutPasswordDTO> getAllWorkmenForCategory(String categoryName) {
        RepairCategory category = repairCategoryRepository.findByName(categoryName).orElseThrow(()-> new NotFoundException("Category not found"));
        return userRepository.getAllByCategoriesContaining(category)
                .stream()
                .map(user -> mapper.map(user,UserWithoutPasswordDTO.class))
                .collect(Collectors.toList());
    }


    public UserEditDTO editUser(UserEditDTO dto, int id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
        if (!dto.getFirstName().matches(UtilityUser.nameRegex)) {
            throw new BadRequestException("Invalid first name. You should use only letters.");
        }

        if (!dto.getLastName().matches(UtilityUser.nameRegex)) {
            throw new BadRequestException("Invalid last name. You should use only letters.");
        }
        String phoneNumber = UtilityUser.validateAndRestyleNumber(dto.getPhoneNumber());
        if (!UtilityUser.isEmailValid(dto.getEmail())){
            throw new BadRequestException("Invalid email");
        }
        if(dto.getAge()<0){
            throw new BadRequestException("Invalid age given!");
        }

        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAge(dto.getAge());
        user.setPhoneNumber(phoneNumber);
        user.setProfilePhoto(dto.getProfilePhoto());
        user.setEmail(dto.getEmail());
        user = userRepository.save(user);
        return mapper.map(user,UserEditDTO.class);
    }

    public void deleteProfile(int id) {
      userRepository.deleteById(id);
    }


    @Transactional
    public UserAdditionalInfoDTO addCategory(int id, String categoryName) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
        RepairCategory category = repairCategoryRepository.findByName(categoryName).orElseThrow(()-> new BadRequestException("Category not found"));
        if (!user.getRoleName().equals("workman")){
            throw new UnauthorizedException("User isn't a workman");
        }
        if (user.getCategories().contains(category)){
            throw new BadRequestException("User already has this category");
        }
        user.getCategories().add(category);
        user = userRepository.save(user);
        category.getUsers().add(user);
        repairCategoryRepository.save(category);
        return  mapper.map(user, UserAdditionalInfoDTO.class);
    }

    public RateResponseDTO createRate(int raterId, int ratedId, RateDTO rating) {
        if (rating.getRating() > 10 || rating.getRating() < 0){
            throw new BadRequestException("Unsupported rating value");
        }
        if (ratingRepository.findByUserIdAndRatedWorkmanId(raterId,ratedId).isPresent()){
            throw new BadRequestException("This user already rated this workman");
        }
        User rater = userRepository.findById(raterId).orElseThrow(()->new UnauthorizedException("Rater not found"));
        User rated = userRepository.findById(ratedId).orElseThrow(()->new UnauthorizedException("Rated user not found"));
        if (!rated.getRoleName().equals("workman")){
            throw new BadRequestException("Only workmen can be rated");
        }
        if (raterId == ratedId){
            throw new BadRequestException("User can't rate himself");
        }
        Rating rate=new Rating();
        rate.setRating(rating.getRating());
        rate.setRatedWorkman(rated);
        rate.setUser(rater);
        rate = ratingRepository.save(rate);
        return mapper.map(rate,RateResponseDTO.class);
    }
}
