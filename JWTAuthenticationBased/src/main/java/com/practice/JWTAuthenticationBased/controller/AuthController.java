package com.practice.JWTAuthenticationBased.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.JWTAuthenticationBased.entity.AuthRequest;
import com.practice.JWTAuthenticationBased.service.CustomUserDetailsService;
import com.practice.JWTAuthenticationBased.util.JwtUtil;

@RestController
@RequestMapping("/authentication")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private CustomUserDetailsService customUserDetailsService;
    
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }   

    @PostMapping("/auth")
    public String getAuthenticate(@RequestBody AuthRequest authRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        
        if(authentication.isAuthenticated())
        {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
            String role= userDetails.getAuthorities().iterator().next().getAuthority();
            return jwtUtil.generateToken(authRequest.getUsername(), role);
        }
        else{
            return "User Name Not Found";
        }
    }

    @GetMapping("/admin")
    public String getHelloAdmin()
    {
        return "Hello ADMIN!!";
    }
    @GetMapping("/user")
    public String getHelloUser()
    {
        return "Hello USER!!";
    }
}
