package com.rest.api.store.util;

import com.rest.api.store.entity.CartProduct;
import com.rest.api.store.entity.Product;

import java.math.BigDecimal;
import java.util.Random;

public class TestUtil {
    public static Product generateRandomProduct() {
        Random r = new Random();

        Product product = new Product();
        product.setId(r.nextLong());

        product.setTitle(getRandomString());
        product.setQuantityAvailable(r.nextInt());
        product.setPrice(BigDecimal.valueOf(r.nextLong()));
        return product;
    }

    public static CartProduct generateRandomCartProduct() {
        byte[] array = new byte[7];
        Random r = new Random();
        new Random().nextBytes(array);
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(r.nextLong());
        cartProduct.setProduct_id(r.nextLong());
        cartProduct.setQuantity(r.nextInt());
        return cartProduct;
    }

    public static CartProduct generateRandomCartProduct(Product product) {
        byte[] array = new byte[7];
        Random r = new Random();
        new Random().nextBytes(array);
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(r.nextLong());
        cartProduct.setProduct_id(product.getId());
        cartProduct.setQuantity(r.nextInt());
        return cartProduct;
    }

    private static String getRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
