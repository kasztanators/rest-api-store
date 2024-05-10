package com.rest.api.store.service;

import com.rest.api.store.dto.OrderDTO;
import com.rest.api.store.entity.CartProduct;
import com.rest.api.store.entity.Customer;
import com.rest.api.store.entity.Product;
import com.rest.api.store.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock
    private ProductService productService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateOrder() {
        Product product1 = TestUtil.generateRandomProduct();
        Product product2 = TestUtil.generateRandomProduct();
        when(productService.getProductById(product1.getId())).thenReturn(product1);
        when(productService.getProductById(product2.getId())).thenReturn(product2);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");
        when(customerService.getLoggedCustomer()).thenReturn(customer);

        List<CartProduct> cartProducts = new ArrayList<>();
        CartProduct e1 = TestUtil.generateRandomCartProduct();
        e1.setProduct_id(product1.getId());
        cartProducts.add(e1);
        CartProduct e2 = TestUtil.generateRandomCartProduct();
        cartProducts.add(e2);
        e2.setProduct_id(product2.getId());

        OrderDTO orderDTO = orderService.createOrder(cartProducts);

        assertNotNull(orderDTO);
        assertEquals(2, orderDTO.getProductList().size());
        assertNotNull(orderDTO.getTotalPrice());
        assertNotNull(orderDTO.getOrderTs());
        assertNotNull(orderDTO.getCustomer());
        assertEquals(1L, orderDTO.getCustomer().getId());
        assertEquals("test@example.com", orderDTO.getCustomer().getEmail());
    }
}
