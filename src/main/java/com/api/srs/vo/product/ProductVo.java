package com.api.srs.vo.product;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ProductVo {
    private BigInteger id;
    private String name;
    private BigDecimal price;
    private Integer amount;

    public ProductVo(BigInteger id, String name, BigDecimal price, Integer amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
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
