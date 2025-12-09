package com.example.woltweb.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailsDto {
    private Integer id;
    private String name;
    private Double price;
    private String status;
    private LocalDateTime dateCreated;

    private Integer buyerId;
    private String buyerName;

    private Integer restaurantId;
    private String restaurantName;

    private List<OrderItemDto> items;

    public OrderDetailsDto(Integer id,
                           String name,
                           Double price,
                           String status,
                           LocalDate dateCreated,
                           Integer buyerId,
                           String buyerName,
                           Integer restaurantId,
                           String restaurantName,
                           List<OrderItemDto> items) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.dateCreated = dateCreated.atStartOfDay();
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.items = items;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public String getStatus() { return status; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public Integer getBuyerId() { return buyerId; }
    public String getBuyerName() { return buyerName; }
    public Integer getRestaurantId() { return restaurantId; }
    public String getRestaurantName() { return restaurantName; }
    public List<OrderItemDto> getItems() { return items; }
}
