package com.rest.api.store.service;

import com.rest.api.store.dto.AddProductToCartDTO;
import com.rest.api.store.dto.GetCartDTO;
import com.rest.api.store.dto.OrderDTO;
import com.rest.api.store.entity.Cart;
import com.rest.api.store.entity.CartProduct;
import com.rest.api.store.entity.Customer;
import com.rest.api.store.entity.Product;
import com.rest.api.store.exception.CartIsEmptyException;
import com.rest.api.store.exception.ProductUnavailableException;
import com.rest.api.store.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final CartProductService cartProductService;
    private final OrderService orderService;

    public OrderDTO checkout() throws CartIsEmptyException {
        Cart cart = getCart();

        if (cart.getProducts() == null || cart.getProducts().isEmpty()) {
            throw new CartIsEmptyException();
        }

        OrderDTO orderDTO = orderService.createOrder(cart.getProducts());
        cart.getProducts().clear();
        cartRepository.save(cart);
        return orderDTO;
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
        if (cart.getProducts() == null) {
            products = new ArrayList<>();
        }
        products.add(cartProduct);
        cart.setProducts(products);
        cartRepository.save(cart);
    }

    @Transactional
    public void modifyProductInCart(AddProductToCartDTO addProductToCartDTO) {
        Cart cart = getCart();
        Optional<CartProduct> cartProductOptional = cartProductService
                .findCartProductById(cart, addProductToCartDTO.productID());

        if (cartProductOptional.isPresent()) {
            CartProduct cartProduct = cartProductOptional.get();
            Integer oldQuantity = cartProduct.getQuantity();
            Integer newQuantity = addProductToCartDTO.quantity();

            Product product = productService.getProductById(addProductToCartDTO.productID());
            productService.checkProductAvailability(
                    product,
                    newQuantity - oldQuantity);
            productService.updateProductQuantity(product, (newQuantity - oldQuantity));
            cartProduct.setQuantity(newQuantity);
            cartRepository.save(cart);
        } else {
            addToCart(addProductToCartDTO);
        }
    }


    private Cart getCart() {
        Customer loggedCustomer = customerService.getLoggedCustomer();
        Cart cart = loggedCustomer.getCart();
        return cart;
    }

    public GetCartDTO getCartResponse() {
        Cart cart = getCart();

        return GetCartDTO.builder()
                .productList(cart.getProducts()
                        .stream()
                        .map(cartProductService::MapCartProductToGetCartProductDTO)
                        .toList()).build();
    }

    @Transactional
    public void removeFromCart(Long id) {
        Cart cart = getCart();
        Optional<CartProduct> cartProductOptional = cartProductService
                .findCartProductById(cart, id);
        if (cartProductOptional.isPresent()) {
            CartProduct cartProduct = cartProductOptional.get();
            Product product = productService.getProductById(cartProduct.getProduct_id());
            productService.updateProductQuantity(product, cartProduct.getQuantity() * -1);
            cart.getProducts().remove(cartProduct);
            cartRepository.save(cart);
        } else {
            throw new ProductUnavailableException("No such a product inside cart");
        }
    }
}
