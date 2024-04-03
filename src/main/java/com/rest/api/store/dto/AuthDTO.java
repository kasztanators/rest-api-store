package com.rest.api.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.Serializable;

public record AuthDTO(@JsonProperty("email") String email,
                      @JsonProperty("password") String password) {
}
