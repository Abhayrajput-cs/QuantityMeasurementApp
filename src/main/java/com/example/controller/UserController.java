package com.example.controller;

import com.example.dto.LoginDTO;
import com.example.dto.RegisterDTO;
import com.example.service.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody LoginDTO dto) {
        return userService.login(dto);
    }

    // ✅ Use act for OAuth login
    @GetMapping("/google/success")
    public String googleLoginSuccess(@AuthenticationPrincipal OAuth2User user) {
        String email = user.getAttribute("email");
        String token = userService.googleLogin(email, user.getAttribute("name"));

        // Spring will send an actual redirect to browser
        return "redirect:http://localhost:3000/auth?token=" + token;
    }
    @GetMapping("/api/v1/users/google/success")
    public ResponseEntity<?> success(Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("Authentication is NULL ❌");
        }

        String email = authentication.getName();
        System.out.println("✅ GOOGLE LOGIN SUCCESS: " + email);

        String token = ""; // temp userId

        return ResponseEntity.ok(Map.of("token", token));
    }
}