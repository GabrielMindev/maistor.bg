package com.project.maistorbg.model.repositories;

import com.project.maistorbg.model.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Integer> {
    Optional<City> findByCityName(String cityName);
}
