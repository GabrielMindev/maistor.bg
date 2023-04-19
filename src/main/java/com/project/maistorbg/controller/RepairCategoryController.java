package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.RepairCategoryDTOs.AddRepairCategoryDTO;
import com.project.maistorbg.model.DTOs.RepairCategoryDTOs.EditRepairCategoryDTO;
import com.project.maistorbg.model.DTOs.RepairCategoryDTOs.RepairCategoryInfoDTO;
import com.project.maistorbg.model.entities.RepairCategory;
import com.project.maistorbg.service.RepairCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RepairCategoryController {

    @Autowired
    RepairCategoryService repairCategoryService;

    @Autowired
    ModelMapper mapper;

    @GetMapping("/categories/all")
    public ResponseEntity<List<RepairCategoryInfoDTO>> getAllRepairCategories() {
        List<RepairCategory> categories = repairCategoryService.getAllRepairCategories();
        List<RepairCategoryInfoDTO> categoryDTOs = categories.stream()
                .map(repairCategory -> mapper.map(repairCategory, RepairCategoryInfoDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @PostMapping("/categories")
    public ResponseEntity<RepairCategoryInfoDTO> addCategory(@RequestBody AddRepairCategoryDTO dto) {
        RepairCategory repairCategory = mapper.map(dto, RepairCategory.class);
        RepairCategory savedRepairCategory = repairCategoryService.addRepairCategory(repairCategory);
        RepairCategoryInfoDTO categoryDTO = mapper.map(savedRepairCategory, RepairCategoryInfoDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }

    @PutMapping("/categories/{repairCategoryId}")
    public ResponseEntity<RepairCategoryInfoDTO> editCategory(@PathVariable int repairCategoryId, @RequestBody EditRepairCategoryDTO dto) {
        RepairCategory repairCategory = mapper.map(dto, RepairCategory.class);
        RepairCategory savedRepairCategory = repairCategoryService.editRepairCategory(repairCategoryId, repairCategory);
        RepairCategoryInfoDTO categoryDTO = mapper.map(savedRepairCategory, RepairCategoryInfoDTO.class);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping("/categories/{repairCategoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int repairCategoryId) {
        repairCategoryService.deleteRepairCategory(repairCategoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
