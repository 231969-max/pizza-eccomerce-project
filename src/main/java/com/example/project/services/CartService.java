package com.example.project.services;

import com.example.project.models.Cart;
import com.example.project.models.CartItem;
import com.example.project.models.Product;
import com.example.project.models.User;
import com.example.project.repositories.CartItemRepository;
import com.example.project.repositories.CartRepository;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
            UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart getCart(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart(user);
                    return cartRepository.save(newCart);
                });
    }

    @Transactional
    public Cart addToCart(String userEmail, long productId, int quantity) {
        Cart cart = getCart(userEmail);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(cart, product, quantity, product.getPrice());
            cart.addItem(newItem);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateQuantity(String userEmail, long cartItemId, int change) {
        Cart cart = getCart(userEmail);
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Item does not belong to user's cart");
        }

        int newQuantity = item.getQuantity() + change;
        if (newQuantity <= 0) {
            cart.removeItem(item);
        } else {
            item.setQuantity(newQuantity);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeItem(String userEmail, long cartItemId) {
        Cart cart = getCart(userEmail);
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Item does not belong to user's cart");
        }

        cart.removeItem(item);
        return cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(String userEmail) {
        Cart cart = getCart(userEmail);
        cart.getItems().clear();
        cart.recalculateTotal();
        cartRepository.save(cart);
    }
}
