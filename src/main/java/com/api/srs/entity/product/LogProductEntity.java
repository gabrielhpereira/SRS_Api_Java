package com.api.srs.entity.product;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class LogProductEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JoinColumn(name = "id_product")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    @Column
    private String description;

    @Column
    private Date date;

    public LogProductEntity() {
    }

    private LogProductEntity(Builder builder) {
        this.id = builder.id;
        this.product = builder.product;
        this.description = builder.description;
        this.date = builder.date;
    }

    public static class Builder {
        private Integer id;
        private ProductEntity product;
        private String description;
        private Date date;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder product(ProductEntity product) {
            this.product = product;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public LogProductEntity build() {
            return new LogProductEntity(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }
}