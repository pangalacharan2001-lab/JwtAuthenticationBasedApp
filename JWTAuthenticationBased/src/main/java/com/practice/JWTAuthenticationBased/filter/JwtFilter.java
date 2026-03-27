package com.practice.JWTAuthenticationBased.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.practice.JWTAuthenticationBased.service.CustomUserDetailsService;
import com.practice.JWTAuthenticationBased.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
            
    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
            String authHeader = request.getHeader("Authorization");
            if(authHeader !=null && authHeader.startsWith("Bearer ")){
                System.out.println("Extracting token from Bearer token");
                String token = authHeader.substring(7);
                String username = jwtUtil.extractUsername(token);

                if(username !=null && SecurityContextHolder.getContext().getAuthentication() ==null){
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    if(jwtUtil.validateToken(token, userDetails)){
                        UsernamePasswordAuthenticationToken authenticationToken= 
                                new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                    else{
                        System.out.println("Token is Invalid");
                    }
                }

            }
            filterChain.doFilter(request, response);
        }
}
