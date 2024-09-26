package org.freekode.inposttask.domain;

import org.freekode.inposttask.domain.discount.AmountBasedDiscountStrategy;
import org.freekode.inposttask.domain.discount.DiscountStrategy;
import org.freekode.inposttask.domain.discount.PercentageBasedDiscountStrategy;
import org.freekode.inposttask.domain.discount.ProductDiscount;
import org.freekode.inposttask.domain.product.Product;
import org.freekode.inposttask.infrastructure.DiscountsConfiguration;
import org.freekode.inposttask.infrastructure.InMemoryDiscountRepository;
import org.freekode.inposttask.infrastructure.InMemoryProductRepository;
import org.freekode.inposttask.infrastructure.ProductsConfiguration;
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

class PriceCalculatorTest {
    private final InMemoryProductRepository productRepository = new InMemoryProductRepository(new ProductsConfiguration(List.of()));

    private final InMemoryDiscountRepository discountRepository = new InMemoryDiscountRepository(new DiscountsConfiguration(Map.of(), Map.of()));

    private final PriceCalculator priceCalculator = new PriceCalculator(productRepository, discountRepository);

    private Product product;

    @BeforeEach
    void beforeEach() {
        product = new Product(UUID.randomUUID(), BigDecimal.valueOf(123));
        productRepository.addProduct(product);
        productRepository.addProduct(new Product(UUID.randomUUID(), BigDecimal.valueOf(321)));
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
        Optional<TotalPrice> actualPrice = priceCalculator.getPrice(product.id(), productAmount);

        // then
        assertTrue(actualPrice.isPresent());
        assertEquals(product.getTotalPrice(productAmount), actualPrice.get());
    }

    @Test
    void getPriceWithAmountBasedDiscountTest() {
        // given
        int productAmount = 13;
        int discountAmount = 7;

        DiscountStrategy discountStrategy = new AmountBasedDiscountStrategy(List.of(
                new ProductDiscount(10, 5),
                new ProductDiscount(11, discountAmount)));
        discountRepository.addDiscount(product.id(), discountStrategy);

        // when
        Optional<TotalPrice> actualPrice = priceCalculator.getPrice(product.id(), productAmount);

        // then
        assertTrue(actualPrice.isPresent());
        TotalPrice expectedPrice = new TotalPrice(product.getTotalPrice(productAmount).price().subtract(BigDecimal.valueOf(discountAmount)));
        assertEquals(expectedPrice, actualPrice.get());
    }

    @Test
    void getPriceWithPercentageBasedDiscountTest() {
        // given
        int productAmount = 17;
        int discountPercentage = 5;

        DiscountStrategy discountStrategy = new PercentageBasedDiscountStrategy(List.of(new ProductDiscount(10, discountPercentage)));
        discountRepository.addDiscount(product.id(), discountStrategy);

        // when
        Optional<TotalPrice> actualPrice = priceCalculator.getPrice(product.id(), productAmount);

        // then
        assertTrue(actualPrice.isPresent());

        BigDecimal totalPrice = product.getTotalPrice(productAmount).price();
        TotalPrice expectedTotalPrice = new TotalPrice(product.price().multiply(
                BigDecimal.valueOf(productAmount)).subtract(
                totalPrice.multiply(BigDecimal.valueOf(discountPercentage / 100.0))));

        assertEquals(expectedTotalPrice, actualPrice.get());
    }

    @Test
    void getPriceForUnknownProductTest() {
        // when
        Optional<TotalPrice> price = priceCalculator.getPrice(UUID.randomUUID(), 1);

        // then
        assertFalse(price.isPresent());
    }
}