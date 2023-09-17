package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authservice;

    @PostMapping("/signup")
    public Long signup(@RequestBody RegisterRequest registerRequest){
        return authservice.signup(registerRequest);
//        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    @GetMapping("/testing")
    public String testing(){
        return "dkjnf";
    }

    @GetMapping("/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authservice.verifyAccount(token);
        return new ResponseEntity<>("activated user",HttpStatus.OK);
    }
}
