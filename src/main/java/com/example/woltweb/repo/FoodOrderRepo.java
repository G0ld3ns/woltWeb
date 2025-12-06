package com.example.woltweb.repo;

import com.example.woltweb.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepo extends JpaRepository<FoodOrder, Integer> {
}
