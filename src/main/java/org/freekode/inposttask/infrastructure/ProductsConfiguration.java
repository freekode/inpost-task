package org.freekode.inposttask.infrastructure;

import org.freekode.inposttask.domain.product.Product;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("app")
public record ProductsConfiguration(
        List<Product> products
) {
}
