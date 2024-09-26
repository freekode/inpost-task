package org.freekode.inposttask.domain.discount;

import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal applyDiscount(BigDecimal productPrice, Integer productAmount);
}
