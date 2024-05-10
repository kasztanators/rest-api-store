package com.rest.api.store.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.store.entity.Product;
import com.rest.api.store.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @WithMockUser(username = "user", roles = "CUSTOMER")
    void testGetAllProducts() throws Exception {
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setTitle("Product 1");
        product.setId(1L);
        List<Product> products = List.of(
                product
        );
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("""
                        [{"id":1,"title":"Product 1","price":10.0,"quantityAvailable":null}]
                        """));
    }

    @Test
    @WithMockUser(username = "user", roles = "CUSTOMER")
    void testGetProductById() throws Exception {
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setTitle("Product 1");
        product.setId(1L);

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("""
                        {"id":1,"title":"Product 1","price":10.0,"quantityAvailable":null}
                        """));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddProduct() throws Exception {
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(10.00));
        product.setTitle("Product 1");
        product.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isCreated());

        verify(productService).addProduct(product);
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}