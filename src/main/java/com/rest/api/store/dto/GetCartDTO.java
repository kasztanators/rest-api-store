package com.rest.api.store.dto;

import com.rest.api.store.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class GetCartDTO {
    Long cartId;
    List<Product> productList;
}
