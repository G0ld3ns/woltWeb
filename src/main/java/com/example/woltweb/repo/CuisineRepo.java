package com.example.woltweb.repo;

import com.example.woltweb.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuisineRepo extends JpaRepository<Cuisine, Integer> {
        List<Cuisine> getCuisineByRestaurantMenu_Id(int id);
}
