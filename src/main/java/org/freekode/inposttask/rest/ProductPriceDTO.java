package org.freekode.inposttask.rest;

import java.math.BigDecimal;

public class ProductPriceDTO {
    BigDecimal price;

    public ProductPriceDTO(BigDecimal price) {
        this.price = price;
    }
}
