package com.rest.api.store.service;

import com.rest.api.store.dto.CustomerDTO;
import com.rest.api.store.dto.OrderDTO;
import com.rest.api.store.dto.ProductDTO;
import com.rest.api.store.entity.CartProduct;
import com.rest.api.store.entity.Customer;
import com.rest.api.store.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final CustomerService customerService;

    public OrderDTO createOrder(List<CartProduct> cartProducts) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setProductList(cartProducts.stream().map(cp -> {
            Product product = productService.getProductById(cp.getProduct_id());
            return ProductDTO.builder()
                    .quantity(cp.getQuantity())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .build();
        }).collect(Collectors.toList()));
        orderDTO.setTotalPrice(countTotalPriceOfOrder(cartProducts));
        orderDTO.setOrderTs(Timestamp.from(Instant.now()));
        Customer customer = customerService.getLoggedCustomer();
        orderDTO.setCustomer(CustomerDTO.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .build());
        return orderDTO;
    }

    private BigDecimal countTotalPriceOfOrder(List<CartProduct> cartProducts) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartProduct p : cartProducts) {
            Product product = productService.getProductById(p.getProduct_id());
            BigDecimal priceOfSingleProduct = product.getPrice();
            totalPrice = totalPrice.add(priceOfSingleProduct.multiply(BigDecimal.valueOf(p.getQuantity())));
        }

        return totalPrice;
    }
}
