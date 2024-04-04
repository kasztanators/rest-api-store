package com.rest.api.store.service;

import com.rest.api.store.entity.CartProduct;
import com.rest.api.store.entity.Product;
import com.rest.api.store.repository.CartProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartProductService {
    private final CartProductRepository cartProductRepository;

    public CartProduct createCartProduct(Product product, int quantity) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct_id(product.getId());
        cartProduct.setQuantity(quantity);
        return cartProductRepository.save(cartProduct);
    }
}
