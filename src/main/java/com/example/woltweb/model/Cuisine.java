package com.example.woltweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cuisine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    protected String name;
    protected Double price;
    protected boolean spicy = false;
    protected boolean vegan = false;
    @ManyToMany (mappedBy = "cuisineList",  cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private List<FoodOrder> orderList;
    @ManyToOne
    private Restaurant restaurantMenu;

    public Cuisine(String name, Double price, boolean spicy, boolean vegan, Restaurant restaurantMenu) {
        this.name = name;
        this.price = price;
        this.spicy = spicy;
        this.vegan = vegan;
        this.restaurantMenu = restaurantMenu;
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

    public boolean isSpicy() {
        return spicy;
    }

    public void setSpicy(boolean spicy) {
        this.spicy = spicy;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public List<FoodOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<FoodOrder> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {return id + ": " + name + ":" + price + "$:" +  spicy + ":" + vegan;}

}

