package com.api.srs.vo.product;

import com.api.srs.entity.product.ProductEntity;

import java.util.Date;

public class LogProductVo {
    private Integer id;
    private String description;
    private String date;
    private String product;

    public LogProductVo(Integer id, String description, Date date, ProductEntity product){
        this.id = id;
        this.description = description.toUpperCase();
        this.date = date.toString();
        this.product = product.getId() + " - " + product.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
