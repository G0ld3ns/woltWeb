package com.example.woltweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@DiscriminatorValue("BasicUser")
public class BasicUser extends User{
    protected String address;
    @JsonIgnore
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<FoodOrder> myOrders;
    @JsonIgnore
    @OneToMany(mappedBy =  "commentOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Review> myReviews;
    @JsonIgnore
    @OneToMany(mappedBy = "feedBack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Review> feedback;

    public BasicUser(String login, String password, String name, String surname, String phoneNumber, String address, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback) {
        super(login, password, name, surname, phoneNumber);
        this.address = address;
        this.myOrders = new ArrayList<>();
        this.myReviews = new ArrayList<>();
        this.feedback = new ArrayList<>();
    }

    public BasicUser(String login, String password, String name, String surname, String phoneNumber, String address) {
        super(login, password, name, surname, phoneNumber);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FoodOrder> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(List<FoodOrder> myOrders) {
        this.myOrders = myOrders;
    }

    public List<Review> getMyReviews() {
        return myReviews;
    }

    public void setMyReviews(List<Review> myReviews) {
        this.myReviews = myReviews;
    }

    public List<Review> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Review> feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
