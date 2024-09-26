package org.freekode.inposttask.infrastructure;

import org.freekode.inposttask.domain.discount.ProductDiscount;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("app.discounts")
public record DiscountsConfiguration(
        Map<String, List<ProductDiscount>> amountBased,
        Map<String, List<ProductDiscount>> percentageBased
) {
}
