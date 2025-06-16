package com.example.Ticketing.Model.DTO.Request;

import jakarta.validation.constraints.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Data
@Schema(description = "Request object for creating a product")
public class ProductRequest {

    @NotBlank
    @Schema(description = "Name of the product", example = "Popcorn")
    private String name;

    @NotNull
    @DecimalMin("0.01")
    @Schema(description = "Price of the product", example = "9.99", minimum = "0.01")
    private BigDecimal price;

    // Getters and Setters (optional if Lombok is working)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
