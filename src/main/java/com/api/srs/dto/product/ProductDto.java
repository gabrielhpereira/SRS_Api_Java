package com.api.srs.dto.product;

import java.math.BigDecimal;
import java.math.BigInteger;

public record ProductDto(
    BigInteger id,
    String name,
    BigDecimal price,
    Integer amount
) {
}
