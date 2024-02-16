package com.rest.api.store.service;

import com.rest.api.store.entity.Cart;
import com.rest.api.store.entity.Product;
import com.rest.api.store.entity.User;
import com.rest.api.store.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor


public class CartService {
    private CartRepository cartRepository;

    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public Cart getCartByUserId(UUID userId) {
        return cartRepository.findByUserId(userId).get();
    }

    public void addToCart(Cart cart, Product product) {
        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

    // Implement other cart operations like removeItemFromCart, updateCartItem, etc.
}
