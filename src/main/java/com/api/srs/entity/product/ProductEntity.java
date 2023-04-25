package com.api.srs.entity.product;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;

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

    public ProductEntity() {

    }

    public ProductEntity(ProductEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.amount = entity.getAmount();
        this.price = entity.getPrice();
    }

    private ProductEntity(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.amount = builder.amount;
    }

    public static class Builder {
        private BigInteger id;
        private String name;
        private BigDecimal price;
        private Integer amount;

        public Builder id(BigInteger id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public ProductEntity build() {
            return new ProductEntity(this);
        }
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
