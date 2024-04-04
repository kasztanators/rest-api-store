package com.rest.api.store.service;

import com.rest.api.store.entity.Product;
import com.rest.api.store.exception.ProductUnavailableException;
import com.rest.api.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductUnavailableException("Product is unavailable!!!"));
    }

    public void updateProductQuantity(Product product, int quantity) {
        int newQuantity = product.getQuantityAvailable() - quantity;
        product.setQuantityAvailable(newQuantity);
        productRepository.save(product);
    }

    public void checkProductAvailability(Product product, int quantity) {
        if (product.getQuantityAvailable() < quantity) {
            throw new ProductUnavailableException("Product is unavailable!!!");
        }
    }
}

