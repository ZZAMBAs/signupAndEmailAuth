package com.example.signupandemailauth.config;

import com.example.signupandemailauth.security.filter.JwtAuthenticationFilter;
import com.example.signupandemailauth.security.filter.UserCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserCheckFilter userCheckFilter;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterAt(userCheckFilter, BasicAuthenticationFilter.class)
                .addFilterAt(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

        http.authorizeHttpRequests(a -> {
            a.requestMatchers("/test")
                    .authenticated()
                    .anyRequest()
                    .permitAll();
        });

        return http.build();
    }
}
