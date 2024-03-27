package com.rest.api.store.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "product_id", updatable = false, nullable = false)
    private Long id;
    private String title;
    private BigDecimal price;
    private Integer quantityAvailable;
}
