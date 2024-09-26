package org.freekode.inposttask.app;

import org.freekode.inposttask.domain.discount.AmountBasedDiscountStrategy;
import org.freekode.inposttask.domain.discount.DiscountStrategy;
import org.freekode.inposttask.domain.discount.PercentageBasedDiscountStrategy;
import org.freekode.inposttask.domain.discount.ProductDiscount;
import org.freekode.inposttask.domain.product.Product;
import org.freekode.inposttask.infrastructure.DiscountsConfiguration;
import org.freekode.inposttask.infrastructure.InMemoryDiscountRepository;
import org.freekode.inposttask.infrastructure.InMemoryProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceServiceTest {
    private final InMemoryProductRepository productRepository = new InMemoryProductRepository();

    private final InMemoryDiscountRepository discountRepository = new InMemoryDiscountRepository(new DiscountsConfiguration(Map.of(), Map.of()));

    private final PriceService priceService = new PriceService(productRepository, discountRepository);

    private Product product;

    @BeforeEach
    void beforeEach() {
        product = new Product(UUID.randomUUID(), BigDecimal.valueOf(123));
        productRepository.addProduct(product);
    }

    @AfterEach
    void afterEach() {
        productRepository.clearProducts();
        discountRepository.clearDiscounts();
    }

    @Test
    void getPriceWithoutDiscountTest() {
        // given
        int productAmount = 2;

        // when
        Optional<BigDecimal> price = priceService.getPrice(product.id(), productAmount);

        // then
        assertTrue(price.isPresent());
        assertEquals(product.price().multiply(BigDecimal.valueOf(productAmount)), price.get());
    }

    @Test
    void getPriceWithAmountBasedDiscountTest() {
        // given
        int productAmount = 13;

        DiscountStrategy discountStrategy = new AmountBasedDiscountStrategy(List.of(
                new ProductDiscount(10, 5),
                new ProductDiscount(11, 7)));
        discountRepository.addDiscount(product.id(), discountStrategy);

        // when
        Optional<BigDecimal> price = priceService.getPrice(product.id(), productAmount);

        // then
        assertTrue(price.isPresent());
        assertEquals(product.price().multiply(BigDecimal.valueOf(productAmount)).subtract(BigDecimal.valueOf(7)), price.get());
    }

    @Test
    void getPriceWithPercentageBasedDiscountTest() {
        // given
        int productAmount = 17;

        DiscountStrategy discountStrategy = new PercentageBasedDiscountStrategy(List.of(new ProductDiscount(10, 5)));
        discountRepository.addDiscount(product.id(), discountStrategy);

        // when
        Optional<BigDecimal> price = priceService.getPrice(product.id(), productAmount);

        // then
        assertTrue(price.isPresent());
        BigDecimal totalPrice = product.price().multiply(BigDecimal.valueOf(productAmount));
        assertEquals(totalPrice.subtract(totalPrice.multiply(BigDecimal.valueOf(5))), price.get());
    }

    @Test
    void getPriceForUnknownProductTest() {
        // when
        Optional<BigDecimal> price = priceService.getPrice(UUID.randomUUID(), 1);

        // then
        assertFalse(price.isPresent());
    }
}