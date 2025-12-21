package com.example.project.controllers;

import com.example.project.dtos.OrderRequest;
import com.example.project.models.Order;
import com.example.project.models.OrderItem;
import com.example.project.models.Product;
import com.example.project.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            List<OrderItem> items = orderRequest.getItems().stream().map(itemRequest -> {
                OrderItem item = new OrderItem();
                Product p = new Product();
                p.setId(itemRequest.getProductId());
                item.setProduct(p);
                item.setQuantity(itemRequest.getQuantity());
                return item;
            }).collect(Collectors.toList());

            Order order = orderService.createOrder(orderRequest.getUserId(), items);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }
}
