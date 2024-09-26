package org.freekode.inposttask.domain.discount;

import org.freekode.inposttask.domain.TotalPrice;

public interface DiscountStrategy {
    TotalPrice applyDiscount(TotalPrice productPrice, Integer productAmount);
}
