package com.example.Ticketing.Service;

import com.example.Ticketing.Model.DTO.Request.ProductRequest;
import com.example.Ticketing.Model.DTO.Response.ProductResponse;
import com.example.Ticketing.Model.Entity.Product;
import com.example.Ticketing.Repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating product with name: {}", request.getName());

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        product = productRepository.save(product);
        log.info("Product created with ID: {}", product.getId());

        return convertToResponse(product);
    }

    public List<ProductResponse> listProducts() {
        log.info("Fetching list of all products...");
        List<ProductResponse> products = productRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        log.info("Total products found: {}", products.size());
        return products;
    }

    private ProductResponse convertToResponse(Product product) {
        log.debug("Converting Product entity to response. ID: {}", product.getId());
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
