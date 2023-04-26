package com.api.srs.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ProductEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private Integer amount;

    public ProductEntity(ProductEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.amount = entity.getAmount();
        this.price = entity.getPrice();
    }
}
