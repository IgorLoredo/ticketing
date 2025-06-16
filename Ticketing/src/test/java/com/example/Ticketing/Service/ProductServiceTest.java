package com.example.Ticketing.Service;

import com.example.Ticketing.Model.DTO.Request.ProductRequest;
import com.example.Ticketing.Model.DTO.Response.ProductResponse;
import com.example.Ticketing.Model.Entity.Product;
import com.example.Ticketing.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductRequest request = new ProductRequest();
        request.setName("Produto Teste");
        request.setPrice(new BigDecimal("99.99"));
        Product product = new Product();
        product.setId(1L);
        product.setName("Produto Teste");
        product.setPrice(new BigDecimal("99.99"));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductResponse response = productService.createProduct(request);
        assertNotNull(response);
        assertEquals("Produto Teste", response.getName());
        assertEquals(new BigDecimal("99.99"), response.getPrice());
    }

    @Test
    void testListProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Produto 1");
        product1.setPrice(new BigDecimal("10.00"));
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Produto 2");
        product2.setPrice(new BigDecimal("20.00"));
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        List<ProductResponse> responses = productService.listProducts();
        assertEquals(2, responses.size());
        assertEquals("Produto 1", responses.get(0).getName());
        assertEquals("Produto 2", responses.get(1).getName());
    }
}
