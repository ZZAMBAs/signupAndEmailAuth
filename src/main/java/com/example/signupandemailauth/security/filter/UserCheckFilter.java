package com.example.signupandemailauth.security.filter;

import com.example.signupandemailauth.security.OtpAuthentication;
import com.example.signupandemailauth.security.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class UserCheckFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    @Value("${auth.jwt.signingKey}")
    private String signingKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String name = request.getHeader("name");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        if (code == null) { // OTP 검증
            Authentication auth = new UsernamePasswordAuthentication(name, password);
            authenticationManager.authenticate(auth);

            SecretKey secretKey = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder()
                    .claim("name", name)
                    .signWith(secretKey)
                    .compact();

            response.setHeader("Authorization", jwt);
        }else{ // OTP 검증
            Authentication auth = new OtpAuthentication(name, code);
            authenticationManager.authenticate(auth);
        }

        // filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }
}
