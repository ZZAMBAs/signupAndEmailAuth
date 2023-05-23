package com.example.signupandemailauth.entity;

import com.example.signupandemailauth.entity.status.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // 이메일 형식
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
