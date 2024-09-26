package org.freekode.inposttask.infrastructure;

import org.freekode.inposttask.domain.discount.AmountBasedDiscountStrategy;
import org.freekode.inposttask.domain.discount.DiscountRepository;
import org.freekode.inposttask.domain.discount.DiscountStrategy;
import org.freekode.inposttask.domain.discount.PercentageBasedDiscountStrategy;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryDiscountRepository implements DiscountRepository {
    private static final Map<UUID, DiscountStrategy> productIdToDiscountMap = new HashMap<>();

    public InMemoryDiscountRepository(DiscountsConfiguration configuration) {
        configuration.amountBased().forEach((key, value) -> {
            DiscountStrategy strategy = new AmountBasedDiscountStrategy(value);
            productIdToDiscountMap.put(UUID.fromString(key), strategy);
        });
        configuration.percentageBased().forEach((key, value) -> {
            DiscountStrategy strategy = new PercentageBasedDiscountStrategy(value);
            productIdToDiscountMap.put(UUID.fromString(key), strategy);
        });
    }

    @Override
    public Optional<DiscountStrategy> findDiscountForProduct(UUID productId) {
        return Optional.ofNullable(productIdToDiscountMap.get(productId));
    }

    public void addDiscount(UUID productId, DiscountStrategy discountStrategy) {
        productIdToDiscountMap.put(productId, discountStrategy);
    }

    public void clearDiscounts() {
        productIdToDiscountMap.clear();
    }
}
