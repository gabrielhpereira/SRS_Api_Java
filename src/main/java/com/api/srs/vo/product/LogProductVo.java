package com.api.srs.vo.product;

import java.math.BigInteger;
import java.util.Date;

public class LogProductVo {
    private final Integer id;
    private final BigInteger productId;
    private final String description;
    private final String date;


    public LogProductVo(Integer id, BigInteger productId, String description, Date date) {
        this.id = id;
        this.productId = productId;
        this.description = description.toUpperCase();
        this.date = date.toString();
    }

    public Integer getId() {
        return id;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
