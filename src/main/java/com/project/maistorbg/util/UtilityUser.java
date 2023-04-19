package com.project.maistorbg.util;

import com.project.maistorbg.model.DTOs.UserDTOs.UserRegisterDTO;
import com.project.maistorbg.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UtilityUser {

    //Ensures that names will be written only with letters.
    public static final String nameRegex = "^[a-zA-Z]+$";


    public static boolean isPasswordMatch(UserRegisterDTO dto) {
        if (dto.getPassword().equals(dto.getConfirmPassword())) {
            return true;
        } else return false;
    }

    public static boolean isEmailValid(String email) {
        String pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(pattern);
    }

    public static boolean isStrongPassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return password.matches(pattern);
    }


    public static String validateAndRestyleNumber(String phoneNumber) {
        String patternString = "^08[789][0-9]{7}$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (!matcher.matches()) {
            throw new BadRequestException("Invalid phone number");
        } else {
            // Replace leading "0" with "+359"
            phoneNumber = "+359" + phoneNumber.substring(1);
            return phoneNumber;
        }
    }
}