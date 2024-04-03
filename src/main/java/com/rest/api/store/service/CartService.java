package com.rest.api.store.service;

import com.rest.api.store.dto.AddProductToCartDTO;
import com.rest.api.store.dto.GetCartDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor


public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerService customerService;

    public String checkout(Authentication authentication){

        return "Checkout was done successfully!";

    }
    public void addToCart(AddProductToCartDTO addProductToCartDTO) {
        Optional<Product> product = productRepository.findById(addProductToCartDTO.productID());
        if (product.isEmpty() || product.get().getQuantityAvailable() < addProductToCartDTO.quantity()){
            throw new ProductUnavailableException("Product is unavailable!!!");
        }

        Cart cart = getCart();


        List<Product> products = cart.getProducts();
        Product productToBeAdded  = product.get();
        products.add(productToBeAdded);
        cart.setProducts(products);

        cartRepository.save(cart);
    }

    public void modifyProductInCart(Long id, AddProductToCartDTO addProductToCartDTO) {
    }

    private Cart getCart() {
        Customer loggedCustomer = customerService.getLoggedCustomer();
        Cart cart = loggedCustomer.getCart();
        if (cart == null){
            cart = new Cart();
            cart.setProducts(new ArrayList<>());
            cart.setCustomer(loggedCustomer);
            customerService.setCustomerCart(loggedCustomer, cart);
        }

        return cart;
    }

    public GetCartDTO getCartResponse() {
        Cart cart = getCart();
        return  GetCartDTO.builder()
                .cartId(cart.getId())
                .productList(cart.getProducts()).build();
    }

}
