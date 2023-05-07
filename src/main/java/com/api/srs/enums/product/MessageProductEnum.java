package com.api.srs.enums.product;

public enum MessageProductEnum {
    NOT_FOUND("Product not found!"),
    NAME_NULL_OR_EMPTY("Name is empty or null!"),
    AMOUNT_NULL_OR_LESS_THAN_ZERO("Amount is null or less than zero!"),
    PRICE_NULL_OR_LESS_THAN_ZERO("Price is null or less than zero!");

    private final String message;

    MessageProductEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
