package com.example.project.services;

import com.example.project.models.Order;
import com.example.project.models.OrderItem;
import com.example.project.models.Product;
import com.example.project.models.User;
import com.example.project.repositories.OrderRepository;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Long userId, List<OrderItem> items) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : items) {
            if (item.getProduct() == null || item.getProduct().getId() == null) {
                throw new IllegalArgumentException("Product ID is missing for an order item");
            }

            Long productId = java.util.Objects.requireNonNull(item.getProduct().getId());
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

            // Re-associate with the managed product entity
            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setOrder(order);

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }

        order.setItems(items);
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
