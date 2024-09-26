package org.freekode.inposttask.domain.discount;

import java.util.Optional;
import java.util.UUID;

public interface DiscountRepository {
    Optional<DiscountStrategy> findDiscountForProduct(UUID productId);
}
