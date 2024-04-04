package com.rest.api.store.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetCartDTO {
    List<GetCartProductDTO> productList;
}
