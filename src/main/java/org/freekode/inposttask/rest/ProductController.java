package org.freekode.inposttask.rest;

import org.freekode.inposttask.app.PriceService;
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
    private final PriceService priceService;

    public ProductController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductPriceDTO> getProductPrice(@PathVariable UUID productId, @RequestParam Integer amount) {
        return priceService.getPrice(productId, amount)
                .map(price -> ResponseEntity.ok(new ProductPriceDTO(price)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
