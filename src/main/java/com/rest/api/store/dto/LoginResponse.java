package com.rest.api.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginResponse {
    private String accessToken;
    private final String tokenType = "Bearer";
}
