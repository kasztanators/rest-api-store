package com.rest.api.store.entity.userdetail;

import com.rest.api.store.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

public record CustomerDetails(Customer customer) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.customer.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.customer.getPassword();
    }

    @Override
    public String getUsername() {
        return this.customer.getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        return this.customer.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.customer.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.customer.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return customer.isEnabled();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDetails that)) return false;
        return Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer);
    }
}
