package com.rest.api.store.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonBackReference;

import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Table(name = "carts")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", updatable = false, nullable = false)
    private Long id;
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Product> products;
    @ToString.Exclude
    @OneToOne(mappedBy = "cart")
    @JsonBackReference
    private Customer customer;
}
