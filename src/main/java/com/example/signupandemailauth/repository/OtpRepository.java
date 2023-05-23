package com.example.signupandemailauth.repository;

import com.example.signupandemailauth.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByName(String name);
}
