package com.rest.api.store.service;

import com.rest.api.store.dto.AddProductToCartDTO;
import com.rest.api.store.dto.GetCartDTO;
import com.rest.api.store.entity.Cart;
import com.rest.api.store.entity.CartProduct;
import com.rest.api.store.entity.Customer;
import com.rest.api.store.entity.Product;
import com.rest.api.store.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final CartProductService cartProductService;

    public String checkout(Authentication authentication) {

        return "Checkout was done successfully!";

    }

    @Transactional
    public void addToCart(AddProductToCartDTO addProductToCartDTO) {
        Product product = productService.getProductById(addProductToCartDTO.productID());
        productService.checkProductAvailability(product, addProductToCartDTO.quantity());

        Cart cart = getCart();
        productService.updateProductQuantity(product, addProductToCartDTO.quantity());
        CartProduct cartProduct = cartProductService.createCartProduct(product, addProductToCartDTO.quantity());
        addProductToCart(cart, cartProduct);
    }

    private void addProductToCart(Cart cart, CartProduct cartProduct) {
        List<CartProduct> products = cart.getProducts();
        products.add(cartProduct);
        cart.setProducts(products);
        cartRepository.save(cart);
    }

    public void modifyProductInCart(Long id, AddProductToCartDTO addProductToCartDTO) {
    }

    private Cart getCart() {
        Customer loggedCustomer = customerService.getLoggedCustomer();
        Cart cart = loggedCustomer.getCart();
        return cart;
    }

    public GetCartDTO getCartResponse() {
        Cart cart = getCart();
        return GetCartDTO.builder()
                .cartId(cart.getId())
                .productList(cart.getProducts()).build();
    }

}
