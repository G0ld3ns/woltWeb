package com.example.woltweb.controllers;

import com.example.woltweb.model.Driver;
import com.example.woltweb.model.User;
import com.example.woltweb.model.VehicleType;
import com.example.woltweb.repo.DriverRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@RestController
public class DriverController {

    @Autowired
    private DriverRepo driverRepo;

    // GET all drivers
    @GetMapping("getAllDrivers")
    public @ResponseBody Iterable<Driver> getAllDrivers() {
        return driverRepo.findAll();
    }

    // GET single driver by id
    @GetMapping("getDriverById/{id}")
    public @ResponseBody Driver getDriverById(@PathVariable int id) {
        return driverRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("driver not found"));
    }

    // INSERT new driver
    @PostMapping("insertDriver")
    public @ResponseBody Driver createDriver(@RequestBody Driver driver) {
        return driverRepo.save(driver);
    }


    @PutMapping("updateDriverById/{id}")
    public @ResponseBody Driver updateDriverById(@PathVariable int id,
                                                 @RequestBody Driver updated) {

        Driver driver = driverRepo.findById(id).orElseThrow(() -> new RuntimeException("driver not found"));
        driver.setLogin(updated.getLogin());
        driver.setName(updated.getName());
        driver.setSurname(updated.getSurname());
        driver.setPassword(updated.getPassword());
        driver.setPhoneNumber(updated.getPhoneNumber());
        driver.setAdmin(updated.isAdmin());
        driver.setAddress(updated.getAddress());
        driver.setVehicleType(updated.getVehicleType());
        driver.setbDate(updated.getbDate());

        driver.setDateUpdated(java.time.LocalDateTime.now());

        return driverRepo.save(driver);
    }


    // DELETE driver
    @DeleteMapping("deleteDriver/{id}")
    public @ResponseBody String deleteDriver(@PathVariable int id) {
        driverRepo.deleteById(id);
        if (driverRepo.existsById(id)) {
            return "Failed to delete driver";
        } else {
            return "Successfully deleted driver";
        }
    }
}
