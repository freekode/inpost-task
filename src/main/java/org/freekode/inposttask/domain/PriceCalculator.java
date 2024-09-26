package org.freekode.inposttask.domain;

import org.freekode.inposttask.domain.discount.DiscountRepository;
import org.freekode.inposttask.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PriceCalculator {
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    public PriceCalculator(ProductRepository productRepository, DiscountRepository discountRepository) {
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
    }

    public Optional<TotalPrice> getPrice(UUID productId, Integer productAmount) {
        return productRepository.findById(productId)
                .map(product -> product.getTotalPrice(productAmount))
                .map(totalPrice -> applyDiscount(productId, productAmount, totalPrice));
    }

    private TotalPrice applyDiscount(UUID productId, Integer productAmount, TotalPrice totalPrice) {
        return discountRepository.findDiscountForProduct(productId)
                .map(discount -> discount.applyDiscount(totalPrice, productAmount))
                .orElse(totalPrice);
    }
}
