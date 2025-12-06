package com.example.woltweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    private  String name;
    private Double price;
    @ManyToOne
    private BasicUser buyer;
    @ManyToMany
    private List<Cuisine> cuisineList;
    @OneToOne
    private Chat chat;
    @ManyToOne
    private Restaurant restaurant;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;

    public FoodOrder(String name, Double price, BasicUser buyer, Restaurant restaurant, OrderStatus orderStatus) {
        this.name = name;
        this.price = price;
        this.buyer = buyer;
        this.restaurant = restaurant;
        this.orderStatus = orderStatus;
        this.dateCreated = LocalDate.now();
        this.dateUpdated = LocalDate.now();
    }

    public FoodOrder(String name, Double price, BasicUser buyer, List<Cuisine> cuisineList, Restaurant restaurant, OrderStatus orderStatus) {
        this.name = name;
        this.price = price;
        this.buyer = buyer;
        this.cuisineList = cuisineList;
        this.restaurant = restaurant;
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BasicUser getBuyer() {
        return buyer;
    }

    public void setBuyer(BasicUser buyer) {
        this.buyer = buyer;
    }

    public List<Cuisine> getCuisineList() {
        return cuisineList;
    }

    public void setCuisineList(List<Cuisine> cuisineList) {
        this.cuisineList = cuisineList;
    }



    @Override
    public String toString() {
        return buyer + "'s ORDER: " +name + " - "  + price + "$ (From: " + restaurant + ")" + " -" + orderStatus + "-";
    }
}
