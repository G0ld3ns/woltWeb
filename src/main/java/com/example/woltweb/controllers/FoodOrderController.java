package com.example.woltweb.controllers;

import com.example.woltweb.model.FoodOrder;
import com.example.woltweb.model.User;
import com.example.woltweb.repo.FoodOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodOrderController {

    @Autowired
    private FoodOrderRepo foodOrderRepo;

    @GetMapping(value =  "getByUserId")

    public Iterable<FoodOrder> getByUserId(){
        return foodOrderRepo.getFoodOrdersByBuyer_Id(2);
    }
}
