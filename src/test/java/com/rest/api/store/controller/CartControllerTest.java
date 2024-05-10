package com.rest.api.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.store.dto.AddProductToCartDTO;
import com.rest.api.store.dto.GetCartDTO;
import com.rest.api.store.dto.GetCartProductDTO;
import com.rest.api.store.dto.OrderDTO;
import com.rest.api.store.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest()
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;


    @Test
    @WithMockUser(username = "user", roles = "CUSTOMER")
    void addToCart() throws Exception {
        AddProductToCartDTO addProductToCartDTO = new AddProductToCartDTO(1L, 2);

        doNothing().when(cartService).addToCart(addProductToCartDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addProductToCartDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "CUSTOMER")
    void modifyCartProduct() throws Exception {
        AddProductToCartDTO addProductToCartDTO = new AddProductToCartDTO(1L, 2);
        doNothing().when(cartService).modifyProductInCart(addProductToCartDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/cart/modify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addProductToCartDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "CUSTOMER")
    void getCartContent() throws Exception {
        GetCartDTO testCart = GetCartDTO.builder()
                .productList(Arrays.asList(GetCartProductDTO.builder()
                        .id(1L).title("title").quantity(2).build()))
                .build();
        when(cartService.getCartResponse()).thenReturn(testCart);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart"))
                .andExpect(MockMvcResultMatchers.content().json("""
                        {"productList":[{"id":1,"title":"title","quantity":2}]}
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "CUSTOMER")
    void checkoutCart() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTotalPrice(BigDecimal.ONE);

        when(cartService.checkout()).thenReturn(orderDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/checkout"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {"productList":null,"totalPrice":1,"orderTs":null,"customer":null}
                                            """))
                .andReturn();

        result.getResponse().getContentAsString();
    }

    @Test
    @WithMockUser(username = "user", roles = "CUSTOMER")
    void removeProductFromCart() throws Exception {
        Long id = 123L;
        doNothing().when(cartService).removeFromCart(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
