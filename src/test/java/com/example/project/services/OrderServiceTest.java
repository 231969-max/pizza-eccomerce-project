package com.example.project.services;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.project.models.Order;
import com.example.project.models.OrderItem;
import com.example.project.models.Product;
import com.example.project.models.User;
import com.example.project.repositories.OrderRepository;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder_ShouldCalculateTotalCorrectly() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Product pizza = new Product();
        pizza.setId(100L);
        pizza.setPrice(new BigDecimal("15.00"));

        OrderItem item = new OrderItem();
        item.setProduct(pizza);
        item.setQuantity(2); // 2 * 15.00 = 30.00

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(100L)).thenReturn(Optional.of(pizza));
        // Mock saving to return the order passed to it
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        var resultOrder = orderService.createOrder(1L, List.of(item));

        // Assert
        assertEquals(new BigDecimal("30.00"), resultOrder.getTotalPrice());
    }

    @Test
    void createOrder_ShouldThrow_WhenItemsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(1L, Collections.emptyList());
        });
    }
}
