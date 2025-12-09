package com.example.woltweb.controllers;

import com.example.woltweb.dto.OrderDetailsDto;
import com.example.woltweb.dto.OrderItemDto;
import com.example.woltweb.model.*;

import com.example.woltweb.repo.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class FoodOrderController {
    @Autowired
    private FoodOrderRepo ordersRepo;
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private BasicUserRepo basicUserRepository;
    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private CuisineRepo cuisineRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;


    @GetMapping(value = "getMenuRestaurant/{id}")
    public Iterable<Cuisine> getRestaurantMenu(@PathVariable int id){
        return cuisineRepo.getCuisineByRestaurantMenu_Id(id);
    }

    @GetMapping(value = "getOrderByUser/{id}")
    public @ResponseBody Iterable<FoodOrder> getOrdersForUser(@PathVariable int id) {
        return ordersRepo.getFoodOrdersByBuyer_Id(id);
    }

    @GetMapping(value = "getMessagesForOrder/{id}")
    public @ResponseBody Iterable<Review> getMessagesForOrder(@PathVariable int id) {
        Chat chat = chatRepo.getChatByFoodOrder_Id(id);
        if(chat == null){
            FoodOrder order = ordersRepo.getReferenceById(id);
            Chat chat1 = new Chat("User " + order.getBuyer().getLogin(), "Chat order" + id, order);
            order.setChat(chat1);
            chatRepo.save(chat1);
        }
        return chatRepo.getChatByFoodOrder_Id(id).getMessages();
    }

    @PostMapping(value = "sendMessage")
    public @ResponseBody String sendMessage(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        var messageText = properties.getProperty("messageText");
        var commentOwner = basicUserRepository.getReferenceById(Integer.valueOf(properties.getProperty("userId")));
        var order = ordersRepo.getReferenceById(Integer.valueOf(properties.getProperty("orderId")));

        Review review = new Review(messageText, commentOwner, order.getChat());
        reviewRepo.save(review);

        return "test";

    }

    @PostMapping("createOrder")
    public @ResponseBody String createOrder(@RequestBody Map<String, Object> payload) {
        try {
            // --- Extract basic fields from JSON ---
            Integer userId = safeConvertToInt(payload.get("userId"));
            Integer restaurantId = safeConvertToInt(payload.get("restaurantId"));
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");

            if (userId == null || restaurantId == null) {
                return "Missing or invalid userId or restaurantId";
            }
            if (items == null || items.isEmpty()) {
                return "Missing items";
            }

            // --- Load entities ---
            var buyer = basicUserRepository.findById(userId).orElse(null);
            var restaurant = restaurantRepo.findById(restaurantId).orElse(null);

            if (buyer == null || restaurant == null) {
                return "Buyer or restaurant not found";
            }

            // --- Build cuisineId -> quantity map from items array ---
            Map<Integer, Integer> cuisineQuantities = new HashMap<>();
            for (Map<String, Object> item : items) {
                Integer cuisineId = safeConvertToInt(item.get("cuisineId"));
                Integer quantity  = safeConvertToInt(item.get("quantity"));
                if (cuisineId != null && quantity != null && quantity > 0) {
                    cuisineQuantities.put(cuisineId, quantity);
                }
            }
            if (cuisineQuantities.isEmpty()) {
                return "No valid items";
            }

            // --- Fetch cuisines by id ---
            List<Cuisine> cuisines = cuisineRepo.findAllById(cuisineQuantities.keySet());

            double price = 0.0;
            for (Cuisine cuisine : cuisines) {
                Integer qty = cuisineQuantities.get(cuisine.getId());
                if (qty != null && cuisine.getPrice() != null) {
                    price += cuisine.getPrice() * qty;
                }
            }

            // --- Create order ---
            FoodOrder order = new FoodOrder();
            order.setName("TEMP_ORDER_NAME");              // temp name
            order.setBuyer(buyer);
            order.setRestaurant(restaurant);
            order.setOrderStatus(OrderStatus.PENDING);     // adjust if enum differs
            order.setDateCreated(LocalDate.now());         // or LocalDateTime if your field is that
            order.setPrice(price);
            //order.setCuisineList(cuisines);                // ManyToMany list
            List<Cuisine> expandedList = new ArrayList<>();

            for (Cuisine cuisine : cuisines) {
                int qty = cuisineQuantities.get(cuisine.getId());
                for (int i = 0; i < qty; i++) {
                    expandedList.add(cuisine);
                }
            }

            order.setCuisineList(expandedList);

            // If you later add field:
            // order.setOrderDetails(new Gson().toJson(cuisineQuantities));

            // Save once to get ID
            order = ordersRepo.save(order);

            // Update name with real ID
            order.setName("ORDER#" + order.getId());
            ordersRepo.save(order);

            return "Order created with ID: " + order.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating order";
        }
    }

    /** Safely convert numbers or number-like strings into Integer. */
    private Integer safeConvertToInt(Object value) {
        if (value == null) return null;
        if (value instanceof Number) return ((Number) value).intValue();
        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("testOrder/{id}")
    public FoodOrder testOrder(@PathVariable int id) {
        FoodOrder o = ordersRepo.findById(id).orElseThrow();
        System.out.println("Cuisines: " + o.getCuisineList().size());
        o.getCuisineList().forEach(c -> System.out.println(c.getName()));
        return o;
    }

    @GetMapping("orderDetails/{id}")
    public @ResponseBody OrderDetailsDto getOrderDetails(@PathVariable int id) {
        FoodOrder order = ordersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // map cuisines -> DTO items (names, prices)
        var items = order.getCuisineList().stream()
                .map(c -> new OrderItemDto(
                        c.getId(),
                        c.getName(),
                        c.getPrice()
                ))
                .collect(Collectors.toList());

        String buyerName = order.getBuyer() != null
                ? order.getBuyer().getName() + " " + order.getBuyer().getSurname()
                : null;

        String restaurantName = order.getRestaurant() != null
                ? order.getRestaurant().getRestaurantName()
                : null;

        return new OrderDetailsDto(
                order.getId(),
                order.getName(),
                order.getPrice(),
                order.getOrderStatus() != null ? order.getOrderStatus().name() : null,
                order.getDateCreated(),
                order.getBuyer() != null ? order.getBuyer().getId() : null,
                buyerName,
                order.getRestaurant() != null ? order.getRestaurant().getId() : null,
                restaurantName,
                items
        );
    }

    @GetMapping("driver/orders/preparing")
    public @ResponseBody List<FoodOrder> getPreparingOrders() {
        return ordersRepo.findByOrderStatus(OrderStatus.PREPARING);
    }

    @PutMapping("driver/orders/{id}/status")
    public @ResponseBody String updateOrderStatus(
            @PathVariable int id,
            @RequestParam("status") OrderStatus status
    ) {
        var order = ordersRepo.findById(id).orElse(null);
        if (order == null) {
            return "Order not found";
        }
        order.setOrderStatus(status);
        ordersRepo.save(order);
        return "OK";
    }

}
