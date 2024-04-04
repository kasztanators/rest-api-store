package com.rest.api.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record AddProductToCartDTO(
        @JsonProperty("id") Long productID,
        @JsonProperty("quantity") Integer quantity) {

}
