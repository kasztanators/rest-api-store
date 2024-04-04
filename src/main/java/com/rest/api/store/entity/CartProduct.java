package com.rest.api.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "cart_products")
public class CartProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "product_id", updatable = false, nullable = false)
    private Long product_id;
    private Integer quantity;
}