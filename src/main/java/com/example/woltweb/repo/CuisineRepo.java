package com.example.woltweb.repo;

import com.example.woltweb.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineRepo extends JpaRepository<Cuisine, Integer> {
}
