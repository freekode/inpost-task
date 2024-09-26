package org.freekode.inposttask.rest;

import org.freekode.inposttask.domain.PriceCalculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/price")
public class ProductController {
    private final PriceCalculator priceCalculator;

    public ProductController(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductPriceDTO> getProductPrice(@PathVariable UUID productId, @RequestParam Integer amount) {
        return priceCalculator.getPrice(productId, amount)
                .map(price -> ResponseEntity.ok(new ProductPriceDTO(price.toString())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
