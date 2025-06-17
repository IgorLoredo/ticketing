package com.example.Ticketing.Model.DTO.Response;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Response object representing a user")
public class UserResponse {

    @Schema(description = "User's unique identifier", example = "1")
    private Long id;

    @Schema(description = "User's full name", example = "John Doe")
    private String name;

    @Schema(description = "User's email address", example = "john@example.com")
    private String email;

    public UserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
