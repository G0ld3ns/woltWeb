package com.example.woltweb.controllers;

import com.example.woltweb.model.Restaurant;
import com.example.woltweb.model.User;
import com.example.woltweb.repo.BasicUserRepo;
import com.example.woltweb.repo.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @GetMapping(value =  "getAllRestaurants")
    public @ResponseBody Iterable<Restaurant> getAllRestaurants(){
        return restaurantRepo.findAll();
    }
}
