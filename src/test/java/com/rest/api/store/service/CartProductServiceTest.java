package com.rest.api.store.service;

import com.rest.api.store.dto.GetCartProductDTO;
import com.rest.api.store.entity.Cart;
import com.rest.api.store.entity.CartProduct;
import com.rest.api.store.entity.Product;
import com.rest.api.store.repository.CartProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class CartProductServiceTest {
    @Mock
    private CartProductRepository cartProductRepository;
    @Mock
    private ProductService productService;
    @InjectMocks
    private CartProductService cartProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateCartProduct() {
        Product product = new Product(1L, "Product 1", BigDecimal.valueOf(10.00));

        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct_id(product.getId());
        cartProduct.setQuantity(2);
        when(cartProductRepository.save(any())).thenReturn(cartProduct);

        CartProduct result = cartProductService.createCartProduct(product, 2);

        assertNotNull(result);
        assertEquals(1L, result.getProduct_id());
        assertEquals(2, result.getQuantity());
    }

    @Test
    void testMapCartProductToGetCartProductDTO() {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct_id(1L);
        cartProduct.setQuantity(3);

        Product product = new Product(1L, "Product 1", BigDecimal.valueOf(10.00));
        when(productService.getProductById(1L)).thenReturn(product);

        GetCartProductDTO result = cartProductService.MapCartProductToGetCartProductDTO(cartProduct);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Product 1", result.getTitle());
        assertEquals(3, result.getQuantity());
    }

    @Test
    void testFindCartProductById_ProductFound() {
        Cart cart = new Cart();
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct_id(1L);
        cartProduct.setQuantity(2);
        cart.setProducts(List.of(cartProduct));

        Optional<CartProduct> result = cartProductService.findCartProductById(cart, 1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getProduct_id());
        assertEquals(2, result.get().getQuantity());
    }

    @Test
    void testFindCartProductById_ProductNotFound() {
        Cart cart = new Cart();

        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct_id(1L);
        cartProduct.setQuantity(2);

        CartProduct cartProduct2 = new CartProduct();
        cartProduct.setProduct_id(2L);
        cartProduct.setQuantity(3);

        cart.setProducts(Arrays.asList(cartProduct, cartProduct2));

        Optional<CartProduct> result = cartProductService.findCartProductById(cart, 3L);

        assertFalse(result.isPresent());
    }
}
