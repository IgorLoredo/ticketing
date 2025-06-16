package com.example.Ticketing.model.dto.Response;

import com.example.Ticketing.Model.DTO.Response.ProductResponse;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class ProductResponseTest {
    @Test
    void testGetAndSetId() {
        ProductResponse response = new ProductResponse(1L, "Soda", new BigDecimal("5.50"));
        response.setId(10L);
        assertEquals(10L, response.getId());
    }

    @Test
    void testGetAndSetName() {
        ProductResponse response = new ProductResponse(1L, "Soda", new BigDecimal("5.50"));
        response.setName("Popcorn");
        assertEquals("Popcorn", response.getName());
    }

    @Test
    void testGetAndSetPrice() {
        ProductResponse response = new ProductResponse(1L, "Soda", new BigDecimal("5.50"));
        response.setPrice(new BigDecimal("9.99"));
        assertEquals(new BigDecimal("9.99"), response.getPrice());
    }

    @Test
    void testConstructor() {
        ProductResponse response = new ProductResponse(2L, "Water", new BigDecimal("2.00"));
        assertEquals(2L, response.getId());
        assertEquals("Water", response.getName());
        assertEquals(new BigDecimal("2.00"), response.getPrice());
    }
}
