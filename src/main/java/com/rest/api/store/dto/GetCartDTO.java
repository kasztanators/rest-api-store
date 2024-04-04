package com.rest.api.store.dto;

import com.rest.api.store.entity.CartProduct;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetCartDTO {
    Long cartId;
    List<CartProduct> productList;
}
