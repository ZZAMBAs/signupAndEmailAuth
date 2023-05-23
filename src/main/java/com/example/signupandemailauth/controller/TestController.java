package com.example.signupandemailauth.controller;

import com.example.signupandemailauth.dto.UserDTO;
import com.example.signupandemailauth.entity.User;
import com.example.signupandemailauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final UserService userService;

    @GetMapping("/test")
    public String test(Authentication a){
        String username = (String) a.getPrincipal();
        return "Hello, " + username + "!";
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody UserDTO userDTO){
        User user = userService.create(userDTO);
        return ResponseEntity.ok(user);
    }
}
