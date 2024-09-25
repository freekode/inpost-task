package org.freekode.inposttask.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(
        UUID id,
        BigDecimal price
) {
}
