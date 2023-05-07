package com.api.srs.enums.employee;

public enum MessageEmployeeEnum {
    NOT_FOUND("Employee not found!"),
    INVALID_CPF("Cpf is invalid!"),
    NAME_NULL_OR_EMPtY("Name cannot be null or empty!"),
    EMAIL_NULL_OR_EMPTY("Email cannot be null or empty!"),
    PHONE_NULL_OR_EMPTY("Phone cannot be null or empty!"),
    ADDRESS_NULL_OR_EMPTY("Address cannot be null or empty!"),
    SECTOR_NULL_OR_EMPTY("Sector cannot be null or empty!");

    private final String message;

    MessageEmployeeEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}