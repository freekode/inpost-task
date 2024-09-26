package org.freekode.inposttask.domain.discount;

public record ProductDiscount(
        Integer amountThreshold,
        Integer discount
) {
}