package com.rest.api.store.controller;

import com.rest.api.store.dto.AddProductToCartDTO;
import com.rest.api.store.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@Valid @RequestBody AddProductToCartDTO addProductToCartDTO,
                                       Authentication authentication) {
        cartService.addToCart(addProductToCartDTO, authentication);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cart/{id}")
    public ResponseEntity<?> modifyCartProduct(@PathVariable Long id,
                                               @RequestBody AddProductToCartDTO addProductToCartDTO,
                                               Authentication authentication) {
        cartService.modifyProductInCart(id, addProductToCartDTO, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCartContent(@Valid @RequestBody AddProductToCartDTO addProductToCartDTO,
                                            Authentication authentication) {
        cartService.addToCart(addProductToCartDTO, authentication);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkoutCart(Authentication authentication) {
        return ResponseEntity.status(OK)
                .body(cartService.checkout(authentication));
    }
}
