package com.api.srs.enums;

public enum MessageGenericEnum {

  NOT_FOUND(" not found!"),

  INVALID_CPF("Cpf is invalid!"),
  INVALID_EMAIL("Email is invalid!"),
  INVALID_PHONE("Phone is invalid!"),

  NAME_NULL_OR_EMPTY("Name cannot be null or empty!"),

  ADDRESS_NULL_OR_EMPTY("Address cannot be null or empty!"),
  SECTOR_NULL_OR_EMPTY("Sector cannot be null or empty!"),

  AMOUNT_NULL_OR_LESS_THAN_ZERO("Amount is null or less than zero!"),
  PRICE_NULL_OR_LESS_THAN_ZERO("Price is null or less than zero!");

  private final String message;

  MessageGenericEnum(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public String getMessage(String completeMessage) {
    return completeMessage + this.message;
  }
}
