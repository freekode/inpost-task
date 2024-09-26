package org.freekode.inposttask.domain.product;

import org.freekode.inposttask.domain.TotalPrice;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(
        UUID id,
        BigDecimal price
) {
    public TotalPrice getTotalPrice(Integer amount) {
        return new TotalPrice(price.multiply(BigDecimal.valueOf(amount)));
    }
}
