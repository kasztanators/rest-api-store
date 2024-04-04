package com.rest.api.store.repository;

import com.rest.api.store.entity.Cart;
import com.rest.api.store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByCustomer(Customer customer);
}
