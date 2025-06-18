package com.example.Ticketing.Service;

import com.example.Ticketing.Exception.BusinessException;
import com.example.Ticketing.Exception.ResourceNotFoundException;
import com.example.Ticketing.Model.DTO.Request.UserRequest;
import com.example.Ticketing.Model.DTO.Response.UserResponse;
import com.example.Ticketing.Model.Entity.User;
import com.example.Ticketing.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest request) {
        log.info("Attempting to create user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Email already registered: {}", request.getEmail());
            throw new BusinessException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user = userRepository.save(user);
        log.info("User created successfully with ID: {}", user.getId());

        return convertToResponse(user);
    }

    public UserResponse getUserById(Long id) {
        log.info("Fetching user by ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User not found");
                });

        log.info("User found: {}", user.getEmail());
        return convertToResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new ResourceNotFoundException("User not found");
                });

        log.info("User found with ID: {}", user.getId());
        return convertToResponse(user);
    }

    private UserResponse convertToResponse(User user) {
        log.debug("Converting User entity to DTO. ID: {}", user.getId());
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
