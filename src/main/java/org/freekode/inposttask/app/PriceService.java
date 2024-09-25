package org.freekode.inposttask.app;

import org.freekode.inposttask.domain.DiscountRepository;
import org.freekode.inposttask.domain.DiscountStrategy;
import org.freekode.inposttask.domain.Product;
import org.freekode.inposttask.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        DiscountStrategy discountStrategy = discountRepository.findDiscountForProduct(productId).orElse(null);
        if (discountStrategy == null) {
            return Optional.of(product.price().multiply(BigDecimal.valueOf(productAmount)));
        }
        BigDecimal price = discountStrategy.applyDiscount(product.price(), productAmount);
        return Optional.of(price);
    }
}
