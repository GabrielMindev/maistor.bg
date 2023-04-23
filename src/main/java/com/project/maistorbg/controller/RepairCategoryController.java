package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.RepairCategoryDTOs.AddRepairCategoryDTO;
import com.project.maistorbg.model.DTOs.RepairCategoryDTOs.EditRepairCategoryDTO;
import com.project.maistorbg.model.DTOs.RepairCategoryDTOs.RepairCategoryInfoDTO;
import com.project.maistorbg.model.entities.RepairCategory;
import com.project.maistorbg.service.RepairCategoryService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RepairCategoryController extends AbstractController {

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
    public ResponseEntity<RepairCategoryInfoDTO> addCategory(@RequestBody AddRepairCategoryDTO dto, HttpSession s) {
        if (dto.getNameRepair() == null || dto.getNameRepair().isBlank() || dto.getNameRepair().length() > 45) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        RepairCategory repairCategory = mapper.map(dto, RepairCategory.class);
        RepairCategory savedRepairCategory = repairCategoryService.addRepairCategory(repairCategory, (int)s.getAttribute("LOGGED_ID"));
        RepairCategoryInfoDTO categoryDTO = mapper.map(savedRepairCategory, RepairCategoryInfoDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }

    @PutMapping("/categories/{repairCategoryId}")
    public ResponseEntity<RepairCategoryInfoDTO> editCategory(@PathVariable int repairCategoryId, @RequestBody EditRepairCategoryDTO dto, HttpSession s) {
        if (dto.getNameRepair() == null || dto.getNameRepair().isBlank() || dto.getNameRepair().length() > 45) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        RepairCategory repairCategory = mapper.map(dto, RepairCategory.class);
        RepairCategory savedRepairCategory = repairCategoryService.editRepairCategory(repairCategoryId, repairCategory, (int)s.getAttribute("LOGGED_ID"));
        RepairCategoryInfoDTO categoryDTO = mapper.map(savedRepairCategory, RepairCategoryInfoDTO.class);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping("/categories/{repairCategoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int repairCategoryId, HttpSession s) {
        repairCategoryService.deleteRepairCategory(repairCategoryId, (int)s.getAttribute("LOGGED_ID"));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
