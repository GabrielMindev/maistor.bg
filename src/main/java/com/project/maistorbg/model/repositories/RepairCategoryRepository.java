package com.project.maistorbg.model.repositories;

import com.project.maistorbg.model.entities.RepairCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepairCategoryRepository extends JpaRepository <RepairCategory, Integer> {


}
