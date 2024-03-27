package com.rest.api.store.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;

@Table(name = "customers")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_enable")
    private boolean enabled;

    @Column(name = "credentials_expired")
    private boolean credentialsNonExpired;

    @Column(name = "account_expired")
    private boolean accountNonExpired;

    @Column(name = "account_locked")
    private boolean locked;

    @JsonIgnore
    @OneToMany(cascade = {PERSIST, MERGE, REMOVE}, fetch = EAGER, mappedBy = "customer", orphanRemoval = true)
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public void addRole(Role role) {
        this.roles.add(role);
        role.setCustomer(this);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this
                .roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleEnum().toString()))
                .collect(Collectors.toSet());
    }

}
