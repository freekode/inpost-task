package org.freekode.inposttask.domain.discount;

import java.math.BigDecimal;
import java.util.List;

public class AmountBasedDiscountStrategy implements DiscountStrategy {
    private final List<ProductDiscount> productDiscounts;

    public AmountBasedDiscountStrategy(List<ProductDiscount> productDiscounts) {
        this.productDiscounts = productDiscounts;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal totalPrice, Integer productAmount) {
        BigDecimal discountedPrice = totalPrice;
        for (ProductDiscount productDiscount : productDiscounts) {
            if (productDiscount.amountThreshold() > productAmount) {
                continue;
            }
            BigDecimal price = totalPrice.subtract(new BigDecimal(productDiscount.discount()));
            discountedPrice = discountedPrice.min(price);
        }

        return discountedPrice;
    }
}
