package com.rest.api.store.service;

import com.rest.api.store.entity.Product;
import com.rest.api.store.exception.ProductUnavailableException;
import com.rest.api.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setTitle("Product 1");
        Product product2 = new Product();
        product2.setTitle("Product 2");
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getTitle());
        assertEquals("Product 2", result.get(1).getTitle());
    }

    @Test
    void testAddProduct() {
        Product product = new Product();
        product.setTitle("Product 1");
        product.setQuantityAvailable(10);

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProduct(product);

        assertNotNull(result);
        assertEquals("Product 1", result.getTitle());
        assertEquals(10, result.getQuantityAvailable());
    }

    @Test
    void testGetProductById_ProductExists() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product 1");
        product.setQuantityAvailable(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Product 1", result.getTitle());
        assertEquals(10, result.getQuantityAvailable());
    }

    @Test
    void testGetProductById_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductUnavailableException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testUpdateProductQuantity() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product 1");
        product.setQuantityAvailable(10);


        productService.updateProductQuantity(product, 3);
        assertEquals(7, product.getQuantityAvailable());
    }

    @Test
    void testCheckProductAvailability_ProductAvailable() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product 1");
        product.setQuantityAvailable(10);


        productService.checkProductAvailability(product, 5);

    }

    @Test
    void testCheckProductAvailability_ProductUnavailable() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product 1");
        product.setQuantityAvailable(2);


        assertThrows(ProductUnavailableException.class, () -> productService.checkProductAvailability(product, 5));
    }
}

