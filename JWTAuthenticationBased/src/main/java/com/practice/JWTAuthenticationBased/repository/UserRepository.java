package com.practice.JWTAuthenticationBased.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.JWTAuthenticationBased.entity.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer>{
        Optional<UserInfo> findByUsername(String username);
}
