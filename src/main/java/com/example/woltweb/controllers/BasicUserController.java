package com.example.woltweb.controllers;

import com.example.woltweb.model.BasicUser;
import com.example.woltweb.repo.BasicUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class BasicUserController {

    @Autowired
    private BasicUserRepo basicUserRepo;


    @GetMapping("getAllBasicUsers")
    public @ResponseBody Iterable<BasicUser> getAllBasicUsers() {
        return basicUserRepo.findAll();
    }


    @GetMapping("getBasicUserById/{id}")
    public @ResponseBody BasicUser getBasicUserById(@PathVariable int id) {
        return basicUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("basic user not found"));
    }


    @PostMapping("insertBasicUser")
    public @ResponseBody BasicUser createBasicUser(@RequestBody BasicUser basicUser) {
        // you can set dateCreated/dateUpdated here if you want
        if (basicUser.getDateCreated() == null) {
            basicUser.setDateCreated(LocalDateTime.now());
        }
        basicUser.setDateUpdated(LocalDateTime.now());
        return basicUserRepo.save(basicUser);
    }

    @PutMapping("updateBasicUserById/{id}")
    public @ResponseBody BasicUser updateBasicUserById(@PathVariable int id,
                                                       @RequestBody BasicUser updated) {

        BasicUser basicUser = basicUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("basic user not found"));

        basicUser.setLogin(updated.getLogin());
        basicUser.setName(updated.getName());
        basicUser.setSurname(updated.getSurname());
        basicUser.setPassword(updated.getPassword());
        basicUser.setPhoneNumber(updated.getPhoneNumber());
        basicUser.setAdmin(updated.isAdmin());


        basicUser.setAddress(updated.getAddress());


        basicUser.setDateUpdated(LocalDateTime.now());

        return basicUserRepo.save(basicUser);
    }

    @DeleteMapping("deleteBasicUser/{id}")
    public @ResponseBody String deleteBasicUser(@PathVariable int id) {
        basicUserRepo.deleteById(id);

        if (basicUserRepo.existsById(id)) {
            return "Failed to delete basic user";
        } else {
            return "Successfully deleted basic user";
        }
    }
}
