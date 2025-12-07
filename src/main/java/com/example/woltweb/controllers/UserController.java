package com.example.woltweb.controllers;

import com.example.woltweb.model.User;
import com.example.woltweb.repo.BasicUserRepo;
import com.example.woltweb.repo.DriverRepo;
import com.example.woltweb.repo.RestaurantRepo;
import com.example.woltweb.repo.UserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value =  "getAllUsers") // tai ka kvieciame http (http://localhost:8080/getAllUsers <---- sitas
    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepo.findAll();
    }


    @GetMapping(value = "validateUser")
    public @ResponseBody User getUserByCredentials(@RequestBody String info){
        System.out.println(info);
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        var login = properties.getProperty("username");
        var psw = properties.getProperty("password");
        return userRepo.getUserByLogin(login, psw);
    }

    @PutMapping(value = "updateUser")
    public @ResponseBody User updateUser(@RequestBody User user){
        userRepo.save(user);
        return userRepo.getReferenceById(user.getId());
    }

    @PutMapping(value = "updateUserById/{id}")
    public @ResponseBody User updateUserById(@RequestBody String info, @PathVariable int id){
        User user = userRepo.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        var name = properties.getProperty("name");
        user.setName(name);
        userRepo.save(user);
        return userRepo.getReferenceById(user.getId());
    }

    @PostMapping(value = "insertUser")
    public @ResponseBody User createUser(@RequestBody User user){
        userRepo.save(user);
        return userRepo.getUserByLogin(user.getLogin(), user.getPassword());

    }

    @DeleteMapping(value = "deleteUser")
    public @ResponseBody String deleteUser(@PathVariable int id){
        userRepo.deleteById(id);
        User user = userRepo.findById(id).orElse(null);
        if (user !=null) {
            return "Failed to delete user";
        } else {
            return "Successfully deleted user";
        }

    }

}
