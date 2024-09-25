package org.freekode.inposttask.infrastructure;

import org.freekode.inposttask.domain.DiscountRepository;
import org.freekode.inposttask.domain.DiscountStrategy;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryDiscountRepository implements DiscountRepository {
    @Override
    public Optional<DiscountStrategy> findDiscountForProduct(UUID productId) {
        return Optional.empty();
    }
}
