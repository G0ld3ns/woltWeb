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

    // GET all basic users (clients)
    @GetMapping("getAllBasicUsers")
    public @ResponseBody Iterable<BasicUser> getAllBasicUsers() {
        return basicUserRepo.findAll();
    }

    // GET one basic user by id
    @GetMapping("getBasicUserById/{id}")
    public @ResponseBody BasicUser getBasicUserById(@PathVariable int id) {
        return basicUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("basic user not found"));
    }

    // INSERT new basic user
    @PostMapping("insertBasicUser")
    public @ResponseBody BasicUser createBasicUser(@RequestBody BasicUser basicUser) {
        // you can set dateCreated/dateUpdated here if you want
        if (basicUser.getDateCreated() == null) {
            basicUser.setDateCreated(LocalDateTime.now());
        }
        basicUser.setDateUpdated(LocalDateTime.now());
        return basicUserRepo.save(basicUser);
    }

    // UPDATE basic user by id (full object from client, like your User/Driver)
    @PutMapping("updateBasicUserById/{id}")
    public @ResponseBody BasicUser updateBasicUserById(@PathVariable int id,
                                                       @RequestBody BasicUser updated) {

        BasicUser basicUser = basicUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("basic user not found"));

        // common User fields
        basicUser.setLogin(updated.getLogin());
        basicUser.setName(updated.getName());
        basicUser.setSurname(updated.getSurname());
        basicUser.setPassword(updated.getPassword());
        basicUser.setPhoneNumber(updated.getPhoneNumber());
        basicUser.setAdmin(updated.isAdmin());

        // BasicUser-specific field
        basicUser.setAddress(updated.getAddress());

        // keep dateCreated as is, update dateUpdated
        basicUser.setDateUpdated(LocalDateTime.now());

        return basicUserRepo.save(basicUser);
    }

    // DELETE basic user
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
