package com.example.Ticketing.Service;

import com.example.Ticketing.Exception.BusinessException;
import com.example.Ticketing.Exception.ResourceNotFoundException;
import com.example.Ticketing.Model.DTO.Request.UserRequest;
import com.example.Ticketing.Model.DTO.Response.UserResponse;
import com.example.Ticketing.Model.Entity.User;
import com.example.Ticketing.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest request) {
        // Verificar se email já está cadastrado
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user = userRepository.save(user);
        return convertToResponse(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertToResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertToResponse(user);
    }

    private UserResponse convertToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}