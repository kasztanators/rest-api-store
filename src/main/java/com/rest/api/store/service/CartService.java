package com.rest.api.store.service;

import com.rest.api.store.dto.AddProductToCartDTO;
import com.rest.api.store.entity.Cart;
import com.rest.api.store.entity.Product;
import com.rest.api.store.entity.Customer;
import com.rest.api.store.exception.ProductUnavailableException;
import com.rest.api.store.repository.CartRepository;
import com.rest.api.store.repository.CustomerRepository;
import com.rest.api.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor


public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public String checkout(Authentication authentication){

        return "Checkout was done successfully!";

    }
    public void addToCart(AddProductToCartDTO addProductToCartDTO, Authentication authentication) {
        Optional<Product> product = productRepository.findById(addProductToCartDTO.productID());
        if (product.isEmpty() || product.get().getQuantityAvailable() < addProductToCartDTO.quantity()){
            throw new ProductUnavailableException("Product is unavailable!!!");
        }
      //  cart.getProducts().add(product);
        cartRepository.save(new Cart());
    }

    public void modifyProductInCart(Long id, AddProductToCartDTO addProductToCartDTO, Authentication authentication) {
    }
}
