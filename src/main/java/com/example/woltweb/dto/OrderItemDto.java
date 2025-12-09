package com.example.woltweb.dto;


public class OrderItemDto {
        private Integer cuisineId;
        private String name;
        private Double price;

        public OrderItemDto(Integer cuisineId, String name, Double price) {
            this.cuisineId = cuisineId;
            this.name = name;
            this.price = price;
        }

        public Integer getCuisineId() {
            return cuisineId;
        }

        public String getName() {
            return name;
        }

        public Double getPrice() {
            return price;
        }
}

