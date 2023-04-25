package com.api.srs.entity.product;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table
public class LogProductEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private BigInteger productId;

    @Column
    private String description;

    @Column
    private String date;

    public LogProductEntity() {
    }

    private LogProductEntity(Builder builder) {
        this.id = builder.id;
        this.productId = builder.productId;
        this.description = builder.description;
        this.date = builder.date;
    }

    public static class Builder {
        private Integer id;
        private BigInteger productId;
        private String description;
        private String date;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder productId(BigInteger productId) {
            this.productId = productId;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public LogProductEntity build() {
            return new LogProductEntity(this);
        }
    }

    public Integer getId() {
        return this.id;
    }

    public BigInteger getProductId() {
        return this.productId;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }
}