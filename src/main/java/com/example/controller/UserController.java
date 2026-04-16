package com.example.controller;

import com.example.dto.LoginDTO;
import com.example.dto.RegisterDTO;
import com.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000") 
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        try {
            String response = userService.register(dto);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
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
}