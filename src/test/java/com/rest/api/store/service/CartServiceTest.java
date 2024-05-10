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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rest.api.store.util.TestUtil.generateRandomCartProduct;
import static com.rest.api.store.util.TestUtil.generateRandomProduct;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductService productService;
    @Mock
    private CustomerService customerService;

    @Mock
    private CartProductService cartProductService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CartService cartService;
    private final Customer customer = new Customer();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(customerService.getLoggedCustomer()).thenReturn(customer);
    }

    @Test
    void testCheckout_CartNotEmpty() throws CartIsEmptyException {
        Cart cart = new Cart();
        cart.setProducts(new ArrayList<>());
        customer.setCart(cart);


        List<CartProduct> products = new ArrayList<>();
        products.add(generateRandomCartProduct());
        cart.setProducts(products);
        when(orderService.createOrder(products)).thenReturn(new OrderDTO());

        assertDoesNotThrow(() -> cartService.checkout());
    }

    @Test
    void testCheckout_CartEmpty() {
        Cart cart = new Cart();
        cart.setProducts(null);
        customer.setCart(cart);

        assertThrows(CartIsEmptyException.class, () -> cartService.checkout());
    }

    @Test
    void testAddToCart() {
        Product product = generateRandomProduct();
        when(productService.getProductById(product.getId())).thenReturn(product);

        CartProduct cartProduct = generateRandomCartProduct();
        when(cartProductService.createCartProduct(product, 2)).thenReturn(cartProduct);

        Cart cart = new Cart();
        cart.setProducts(new ArrayList<>());
        customer.setCart(cart);
        when(cartRepository.save(any())).thenReturn(cart);

        assertDoesNotThrow(() -> cartService.addToCart(new AddProductToCartDTO(product.getId(), product.getQuantityAvailable() - 1)));
    }

    @Test
    void testModifyProductInCart_ProductFound() {
        Cart cart = new Cart();
        List<CartProduct> products = new ArrayList<>();
        CartProduct cartProduct = generateRandomCartProduct();
        products.add(cartProduct);
        cart.setProducts(products);
        when(cartProductService.findCartProductById(cart, cartProduct.getProduct_id())).thenReturn(Optional.of(cartProduct));

        Product product = generateRandomProduct();
        when(productService.getProductById(cartProduct.getProduct_id())).thenReturn(product);
        when(cartRepository.save(any())).thenReturn(cart);
        customer.setCart(cart);
        assertDoesNotThrow(() -> cartService.modifyProductInCart(new AddProductToCartDTO(cartProduct.getProduct_id(), product.getQuantityAvailable() - 5)));
        assertEquals(cartProduct.getQuantity(), product.getQuantityAvailable() - 5);
    }

    @Test
    void testModifyProductInCart_ProductNotFound() {
        Cart cart = new Cart();
        customer.setCart(cart);
        when(cartProductService.findCartProductById(cart, 1L)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> cartService.modifyProductInCart(new AddProductToCartDTO(1L, 3)));
    }

    @Test
    void testGetCartResponse() {
        Cart cart = new Cart();
        cart.setProducts(new ArrayList<>());
        customer.setCart(cart);
        when(cartRepository.save(any())).thenReturn(cart);

        GetCartDTO result = cartService.getCartResponse();

        assertNotNull(result);
        assertTrue(result.getProductList().isEmpty());
    }

    @Test
    void testRemoveFromCart_ProductFound() {
        Cart cart = new Cart();
        customer.setCart(cart);
        List<CartProduct> products = new ArrayList<>();
        CartProduct e = generateRandomCartProduct();

        products.add(e);
        cart.setProducts(products);
        when(cartProductService.findCartProductById(cart, 1L)).thenReturn(Optional.of(generateRandomCartProduct()));

        Product product = generateRandomProduct();
        when(productService.getProductById(1L)).thenReturn(product);
        when(cartRepository.save(any())).thenReturn(cart);

        assertDoesNotThrow(() -> cartService.removeFromCart(1L));
    }

    @Test
    void testRemoveFromCart_ProductNotFound() {
        Cart cart = new Cart();
        when(cartProductService.findCartProductById(cart, 1L)).thenReturn(Optional.empty());

        assertThrows(ProductUnavailableException.class, () -> cartService.removeFromCart(1L));
    }
}
