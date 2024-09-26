package org.freekode.inposttask.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record TotalPrice(
        BigDecimal price
) {
    public BigDecimal getPriceValue() {
        return price.setScale(2, RoundingMode.HALF_DOWN);
    }

    @Override
    public String toString() {
        return getPriceValue().toString();
    }
}
