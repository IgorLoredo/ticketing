package com.example.Ticketing.model.dto.Request;

import com.example.Ticketing.Model.DTO.Request.ProductRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class ProductRequestTest {
    @Test
    void testGetAndSetName() {
        ProductRequest productRequest = new ProductRequest();
        String name = "Popcorn";
        productRequest.setName(name);
        assertEquals(name, productRequest.getName());
    }

    @Test
    void testGetAndSetPrice() {
        ProductRequest productRequest = new ProductRequest();
        BigDecimal price = new BigDecimal("9.99");
        productRequest.setPrice(price);
        assertEquals(price, productRequest.getPrice());
    }

    @Test
    void testDefaultValues() {
        ProductRequest productRequest = new ProductRequest();
        assertNull(productRequest.getName());
        assertNull(productRequest.getPrice());
    }
}
