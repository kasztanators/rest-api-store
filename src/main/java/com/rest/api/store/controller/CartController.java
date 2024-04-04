package com.rest.api.store.controller;

import com.rest.api.store.dto.AddProductToCartDTO;
import com.rest.api.store.dto.GetCartDTO;
import com.rest.api.store.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@Valid @RequestBody AddProductToCartDTO addProductToCartDTO) {
        cartService.addToCart(addProductToCartDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cart/modify")
    public ResponseEntity<?> modifyCartProduct(@Valid @RequestBody AddProductToCartDTO addProductToCartDTO) {
        cartService.modifyProductInCart(addProductToCartDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<GetCartDTO> getCartContent() {
        return ResponseEntity.status(OK).body(cartService.getCartResponse());
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkoutCart() {
        return ResponseEntity.status(OK)
                .body(cartService.checkout());
    }

    @DeleteMapping("cart/{id}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return ResponseEntity.ok().build();
    }
}
