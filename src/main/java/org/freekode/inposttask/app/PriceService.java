package org.freekode.inposttask.app;

import org.freekode.inposttask.domain.discount.DiscountRepository;
import org.freekode.inposttask.domain.discount.DiscountStrategy;
import org.freekode.inposttask.domain.product.Product;
import org.freekode.inposttask.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

@Service
public class PriceService {
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    public PriceService(ProductRepository productRepository, DiscountRepository discountRepository) {
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
    }

    public Optional<BigDecimal> getPrice(UUID productId, Integer productAmount) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return Optional.empty();
        }
        BigDecimal totalPrice = product.price().multiply(BigDecimal.valueOf(productAmount));

        DiscountStrategy discountStrategy = discountRepository.findDiscountForProduct(productId).orElse(null);
        if (discountStrategy != null) {
            totalPrice = discountStrategy.applyDiscount(totalPrice, productAmount);
        }
        return Optional.of(totalPrice.setScale(2, RoundingMode.HALF_DOWN));
    }
}
