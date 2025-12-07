package com.example.woltweb.controllers;

import com.example.woltweb.model.User;
import com.example.woltweb.repo.BasicUserRepo;
import com.example.woltweb.repo.DriverRepo;
import com.example.woltweb.repo.RestaurantRepo;
import com.example.woltweb.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;
    private DriverRepo driverRepo;
    private BasicUserRepo basicUserRepo;
    private RestaurantRepo restaurantRepo;

    @GetMapping(value =  "getAllUsers")

    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepo.findAll();
    }



}
