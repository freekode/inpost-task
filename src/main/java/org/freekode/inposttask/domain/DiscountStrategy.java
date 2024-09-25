package org.freekode.inposttask.domain;

import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal applyDiscount(BigDecimal productPrice, Integer productAmount);
}
