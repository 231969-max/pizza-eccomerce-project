package com.example.project.controllers;

import com.example.project.models.Cart;
import com.example.project.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart.html")
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            Cart cart = cartService.getCart(userDetails.getUsername());
            model.addAttribute("cart", cart);
        }
        return "cart";
    }

    @GetMapping("/api/cart")
    @ResponseBody
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(cartService.getCart(userDetails.getUsername()));
    }

    @PostMapping("/api/cart/add")
    @ResponseBody
    public ResponseEntity<Cart> addToCart(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, Object> payload) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        Long productId = Long.valueOf(payload.get("productId").toString());
        Integer quantity = Integer.valueOf(payload.get("quantity").toString());
        return ResponseEntity.ok(cartService.addToCart(userDetails.getUsername(), productId, quantity));
    }

    @PostMapping("/api/cart/update")
    @ResponseBody
    public ResponseEntity<Cart> updateQuantity(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, Object> payload) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        Long itemId = Long.valueOf(payload.get("itemId").toString());
        Integer change = Integer.valueOf(payload.get("change").toString());
        return ResponseEntity.ok(cartService.updateQuantity(userDetails.getUsername(), itemId, change));
    }

    @PostMapping("/api/cart/remove")
    @ResponseBody
    public ResponseEntity<Cart> removeItem(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, Object> payload) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        Long itemId = Long.valueOf(payload.get("itemId").toString());
        return ResponseEntity.ok(cartService.removeItem(userDetails.getUsername(), itemId));
    }

    @PostMapping("/api/cart/clear")
    @ResponseBody
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        cartService.clearCart(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
