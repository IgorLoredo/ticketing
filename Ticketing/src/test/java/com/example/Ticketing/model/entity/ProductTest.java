package com.example.Ticketing.model.entity;

import com.example.Ticketing.Model.Entity.Product;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    @Test
    void testGettersAndSetters() {
        Product product = new Product();
        Long id = 1L;
        String name = "Produto Teste";
        BigDecimal price = new BigDecimal("99.99");
        LocalDateTime createdAt = LocalDateTime.now();

        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setCreatedAt(createdAt);

        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(createdAt, product.getCreatedAt());
    }

    @Test
    void testDefaultValues() {
        Product product = new Product();
        assertNull(product.getId());
        assertNull(product.getName());
        assertNull(product.getPrice());
        assertNull(product.getCreatedAt());
    }
}
