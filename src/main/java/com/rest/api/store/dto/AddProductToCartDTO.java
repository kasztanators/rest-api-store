package com.rest.api.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;


public record AddProductToCartDTO (
    @JsonProperty("id") Long productID,
    @JsonProperty("quantity") Integer quantity){

}
