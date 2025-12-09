package com.example.woltweb.repo;

import com.example.woltweb.model.FoodOrder;
import com.example.woltweb.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepo extends JpaRepository<FoodOrder, Integer> {
    List<FoodOrder> getFoodOrdersByBuyer_Id(int buyerId);
    List<FoodOrder> findByOrderStatus(OrderStatus status);
}
