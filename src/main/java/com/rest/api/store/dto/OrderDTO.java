package com.rest.api.store.dto;

import com.rest.api.store.entity.Customer;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderDTO {
    private List<ProductDTO> productList;
    private BigDecimal totalPrice;
    private Timestamp orderTs;
    private CustomerDTO customer;
}
