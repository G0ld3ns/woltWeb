package com.example.woltweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("Restaurant")
public class Restaurant extends User {
    //private String name;
    private String address;
    private String restaurantName;
    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType;
    private double deliveryFee;
    private LocalTime openingTime;
    private LocalTime closingTime;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodOrder> foodOrders;
    @OneToMany(mappedBy = "restaurantMenu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cuisine> menu;


    public Restaurant(String login, String password, String name, String surname, String phoneNumber, String address, String restaurantName, CuisineType cuisineType, double deliveryFee,  LocalTime closingTime, LocalTime openingTime) {
        super(login, password, name, surname, phoneNumber);
        this.address = address;
        this.restaurantName = restaurantName;
        this.closingTime = closingTime;
        this.openingTime = openingTime;
        this.deliveryFee = deliveryFee;
        this.cuisineType = cuisineType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }


    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    @Override
    public String toString() {
        return name;
    }
}
