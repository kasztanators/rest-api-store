package com.rest.api.store.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerDTO {
    private Long id;
    private String email;}
