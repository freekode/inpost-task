package org.freekode.inposttask.app;

import org.freekode.inposttask.domain.Product;
import org.freekode.inposttask.infrastructure.InMemoryProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PriceServiceTest {
    @Autowired
    private PriceService priceService;

    @Autowired
    private InMemoryProductRepository productRepository;

    @Test
    void getPriceWithoutDiscountTest() {
        // given
        UUID productId = UUID.randomUUID();
        BigDecimal productPrice = BigDecimal.valueOf(123);
        Product product = new Product(productId, productPrice);
        productRepository.addProduct(product);
        int productAmount = 2;

        // when
        Optional<BigDecimal> price = priceService.getPrice(productId, productAmount);

        assertTrue(price.isPresent());
        assertEquals(productPrice.multiply(BigDecimal.valueOf(productAmount)), price.get());
    }

    @Test
    void getPriceWithAmountBasedDiscountTest() {
        // given
        UUID productId = UUID.randomUUID();
        BigDecimal productPrice = BigDecimal.valueOf(123);
        Product product = new Product(productId, productPrice);
        productRepository.addProduct(product);
        int productAmount = 2;

        // when
        Optional<BigDecimal> price = priceService.getPrice(productId, productAmount);

        assertTrue(price.isPresent());
        assertEquals(productPrice.multiply(BigDecimal.valueOf(productAmount)), price.get());
    }
}