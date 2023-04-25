package com.api.srs.dto.product;

import java.math.BigInteger;

public record LogProductDto(
        Integer id,
        BigInteger productId,
        String description,
        String date
) { }
