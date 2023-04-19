package com.project.maistorbg.service;

import com.project.maistorbg.model.entities.RepairCategory;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.repositories.RepairCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairCategoryService {

    @Autowired
    RepairCategoryRepository repairCategoryRepository;

    public List<RepairCategory> getAllRepairCategories() {
        return repairCategoryRepository.findAll();
    }

    public RepairCategory addRepairCategory(RepairCategory repairCategory) {
        return repairCategoryRepository.save(repairCategory);
    }

    public RepairCategory editRepairCategory(int repairCategoryId, RepairCategory repairCategory) {
        RepairCategory existingRepairCategory = repairCategoryRepository.findById(repairCategoryId)
                .orElseThrow(() -> new NotFoundException("Repair category not found!"));
        existingRepairCategory.setName(repairCategory.getName());
        return repairCategoryRepository.save(existingRepairCategory);
    }

    public void deleteRepairCategory(int repairCategoryId) {
        repairCategoryRepository.deleteById(repairCategoryId);
    }
}
