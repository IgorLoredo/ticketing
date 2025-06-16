package com.example.Ticketing.controller;

import com.example.Ticketing.Model.DTO.Request.ProductRequest;
import com.example.Ticketing.Model.DTO.Response.ProductResponse;
import com.example.Ticketing.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private com.example.Ticketing.Controller.ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductRequest request = mock(ProductRequest.class);
        ProductResponse response = mock(ProductResponse.class);
        when(productService.createProduct(any(ProductRequest.class))).thenReturn(response);

        ResponseEntity<ProductResponse> result = productController.createProduct(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(productService, times(1)).createProduct(request);
    }

    @Test
    void testListProducts() {
        ProductResponse productResponse = mock(ProductResponse.class);
        List<ProductResponse> responseList = Collections.singletonList(productResponse);
        when(productService.listProducts()).thenReturn(responseList);

        ResponseEntity<List<ProductResponse>> result = productController.listProducts();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
        verify(productService, times(1)).listProducts();
    }
}
