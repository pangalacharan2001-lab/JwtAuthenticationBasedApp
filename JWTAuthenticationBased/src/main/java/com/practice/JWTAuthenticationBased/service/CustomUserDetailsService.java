package com.practice.JWTAuthenticationBased.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.JWTAuthenticationBased.entity.UserInfo;
import com.practice.JWTAuthenticationBased.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{


    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("UserRepository injected: " + (userRepository != null));
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Name not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(userInfo.getUsername())
                .password(userInfo.getPassword())
                .roles(userInfo.getUserRole().toUpperCase())
                .build();
    }

}
