package com.api.srs.vo.product;

import java.math.BigInteger;
import java.util.Date;

public class LogProductVo {
    private Integer id;
    private String description;
    private String date;
    private BigInteger productId;

    public LogProductVo(Integer id, String description, Date date, BigInteger productId){
        this.id = id;
        this.description = description.toUpperCase();
        this.date = date.toString();
        this.productId = productId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigInteger getProductId() {
        return this.productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }
}
