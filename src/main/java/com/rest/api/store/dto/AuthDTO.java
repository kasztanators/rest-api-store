package com.rest.api.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthDTO(@JsonProperty("email") String email,
                      @JsonProperty("password") String password) {
}
