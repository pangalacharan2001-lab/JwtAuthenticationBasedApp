package com.practice.JWTAuthenticationBased.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.JWTAuthenticationBased.entity.AuthRequest;
import com.practice.JWTAuthenticationBased.util.JwtUtil;

@RestController
@RequestMapping("/authentication")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth")
    public String getAuthenticate(@RequestBody AuthRequest authRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        
        if(authentication.isAuthenticated())
        {
            return jwtUtil.generateToken(authRequest.getUsername());
        }
        else{
            return "User Name Not Found";
        }
    }

    @GetMapping("/hello")
    public String getHello()
    {
        return "Hello World !!";
    }
}
