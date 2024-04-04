package com.rest.api.store.service;

import com.rest.api.store.dto.GetCartProductDTO;
import com.rest.api.store.entity.Cart;
import com.rest.api.store.entity.CartProduct;
import com.rest.api.store.entity.Product;
import com.rest.api.store.repository.CartProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartProductService {
    private final CartProductRepository cartProductRepository;
    private final ProductService productService;

    public CartProduct createCartProduct(Product product, int quantity) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct_id(product.getId());
        cartProduct.setQuantity(quantity);
        return cartProductRepository.save(cartProduct);
    }

    public GetCartProductDTO MapCartProductToGetCartProductDTO(CartProduct cartProduct) {
        Product product = productService.getProductById(cartProduct.getProduct_id());
        return GetCartProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .quantity(cartProduct.getQuantity()).build();
    }

    public Optional<CartProduct> findCartProductById(Cart cart, Long productId) {
        return cart.getProducts().stream()
                .filter(cartProduct -> cartProduct.getProduct_id() == productId)
                .findFirst();
    }
}
