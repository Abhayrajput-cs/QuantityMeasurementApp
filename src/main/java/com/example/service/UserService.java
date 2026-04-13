package com.example.service;


import com.example.dto.LoginDTO;
import com.example.dto.RegisterDTO;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ REGISTER
    public String register(RegisterDTO dto) {

        if (dto.getEmail() == null || dto.getEmail().isBlank() ||
            dto.getPassword() == null || dto.getPassword().isBlank()) {

            throw new RuntimeException("Email and Password are required");
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);

        return "User Registered Successfully";
    }

    //  LOGIN
    public String login(LoginDTO dto) {

        // 🔥 VALIDATION FIRST
        if (dto.getEmail() == null || dto.getEmail().isBlank() ||
            dto.getPassword() == null || dto.getPassword().isBlank()) {

            throw new RuntimeException("Email and Password are required");
        }

        User user = userRepository.findByEmail(dto.getEmail()).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getId());    }
    public String googleLogin(String email, String name) {

        User user = userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setPassword("");
                return userRepository.save(newUser);
            });

        return jwtUtil.generateToken(user.getEmail(), user.getId());    }
}