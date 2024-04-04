package com.rest.api.store.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCartProductDTO {
    private Long id;
    private String title;
    private Integer quantity;
}
