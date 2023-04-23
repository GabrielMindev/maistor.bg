package com.project.maistorbg.service;

import com.project.maistorbg.model.entities.Comment;
import com.project.maistorbg.model.entities.RepairCategory;
import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.BadRequestException;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import com.project.maistorbg.model.repositories.RepairCategoryRepository;
import com.project.maistorbg.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairCategoryService extends AbstractService {

    public List<RepairCategory> getAllRepairCategories() {
        return repairCategoryRepository.findAll();
    }

    public RepairCategory addRepairCategory(RepairCategory repairCategory, int id) {
        User user = userRepository.findById(id).orElseThrow(()-> new BadRequestException("User not found"));
        if (!user.getRoleName().equals("admin")){
            throw new UnauthorizedException("You are not authorized to add categories. You're not admin.");
        }
        return repairCategoryRepository.save(repairCategory);
    }

    public RepairCategory editRepairCategory(int repairCategoryId, RepairCategory repairCategory, int id) {
        User user = userRepository.findById(id).orElseThrow(()-> new BadRequestException("User not found"));
        if (!user.getRoleName().equals("admin")){
            throw new UnauthorizedException("You are not authorized to edit categories. You're not admin.");
        }
        RepairCategory existingRepairCategory = repairCategoryRepository.findById(repairCategoryId)
                .orElseThrow(() -> new NotFoundException("Repair category not found!"));
        existingRepairCategory.setName(repairCategory.getName());
        return repairCategoryRepository.save(existingRepairCategory);
    }

    public void deleteRepairCategory(int repairCategoryId, int id) {
        User user = userRepository.findById(id).orElseThrow(()-> new BadRequestException("User not found"));
        if (!user.getRoleName().equals("admin")){
            throw new UnauthorizedException("You are not authorized to add categories. You're not admin.");
        }
        RepairCategory category = repairCategoryRepository.findById(repairCategoryId).orElseThrow(() -> new NotFoundException("Category not found!"));
        repairCategoryRepository.deleteById(repairCategoryId);
    }
}

