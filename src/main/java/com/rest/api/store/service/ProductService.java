package com.rest.api.store.service;

import com.rest.api.store.entity.Product;
import com.rest.api.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
