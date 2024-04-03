package com.rest.api.store.service;

import com.rest.api.store.entity.Cart;
import com.rest.api.store.entity.Customer;
import com.rest.api.store.exception.CustomerNotFoundException;
import com.rest.api.store.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;


    public Customer getLoggedCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isEmpty()){
            throw new CustomerNotFoundException("Customer not found!");
        }
        return customer.get();

    }

    public void setCustomerCart(Customer customer, Cart cart) {
        customer.setCart(cart);
        customerRepository.save(customer);
    }

}
