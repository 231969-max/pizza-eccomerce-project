package com.example.project.controllers;

import com.example.project.models.Product;
import com.example.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // Allow frontend to access
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable @org.springframework.lang.NonNull Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}
