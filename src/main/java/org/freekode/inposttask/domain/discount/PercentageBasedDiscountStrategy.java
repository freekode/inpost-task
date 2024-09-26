package org.freekode.inposttask.domain.discount;

import org.freekode.inposttask.domain.TotalPrice;

import java.math.BigDecimal;
import java.util.List;

public class PercentageBasedDiscountStrategy implements DiscountStrategy {
    private final List<ProductDiscount> productDiscounts;

    public PercentageBasedDiscountStrategy(List<ProductDiscount> productDiscounts) {
        this.productDiscounts = productDiscounts;
    }

    @Override
    public TotalPrice applyDiscount(TotalPrice totalPrice, Integer productAmount) {
        BigDecimal discountedPrice = totalPrice.price();
        for (ProductDiscount productDiscount : productDiscounts) {
            if (productDiscount.amountThreshold() > productAmount) {
                continue;
            }
            BigDecimal price = totalPrice.price().subtract(totalPrice.price().multiply(BigDecimal.valueOf(productDiscount.discount() / 100.0)));
            discountedPrice = discountedPrice.min(price);
        }

        return new TotalPrice(discountedPrice);
    }
}
