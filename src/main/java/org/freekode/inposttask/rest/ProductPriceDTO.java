package org.freekode.inposttask.rest;

import java.math.BigDecimal;

public record ProductPriceDTO(
        String price
) {
    public ProductPriceDTO(BigDecimal price) {
        this(price.toString());
    }
}
