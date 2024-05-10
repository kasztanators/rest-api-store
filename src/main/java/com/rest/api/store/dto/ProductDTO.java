package com.rest.api.store.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductDTO {
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
