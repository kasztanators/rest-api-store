package com.rest.api.store.entity.userdetail;


import com.rest.api.store.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service(value = "detailService")
public class CustomerDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {
        return this
                .customerRepository
                .findByEmail(principal)
                .map(CustomerDetails::new) //
                .orElseThrow(() -> new UsernameNotFoundException(principal + " not found"));
    }
}
