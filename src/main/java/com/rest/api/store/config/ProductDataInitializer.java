package com.rest.api.store.config;

import com.rest.api.store.entity.Product;
import com.rest.api.store.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDataInitializer {

    private final ProductRepository productRepository;

    @PostConstruct
    public void initializeData() {
        if (productRepository.count() == 0) {
            List<Product> products = new ArrayList<>();

            for (int i = 1; i <= 5; i++) {
                Product product = new Product();
                product.setTitle("Product " + i);
                product.setPrice(BigDecimal.valueOf(10.00 * i));
                product.setQuantityAvailable(100 - i * 10);
                products.add(product);
            }

            productRepository.saveAll(products);
        }
    }
}
