package com.example.project.services;

import com.example.project.models.Product;
import com.example.project.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product getProductById(@NonNull Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @NonNull
    public Product saveProduct(@NonNull Product product) {
        return productRepository.save(product);
    }

    public List<Product> searchProducts(String query) {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()) ||
                        p.getDescription().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
