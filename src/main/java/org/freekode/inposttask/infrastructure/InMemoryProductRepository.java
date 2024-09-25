package org.freekode.inposttask.infrastructure;

import org.freekode.inposttask.domain.Product;
import org.freekode.inposttask.domain.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private static final Map<UUID, Product> idToProductMap = new HashMap<>();

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(idToProductMap.get(id));
    }

    public void addProduct(Product product) {
        idToProductMap.put(product.id(), product);
    }
}
