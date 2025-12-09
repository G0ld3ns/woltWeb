package com.example.woltweb.repo;

import com.example.woltweb.model.Chat;
import com.example.woltweb.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {
    Restaurant findById(int id);

}
