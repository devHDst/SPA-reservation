package com.restapi.cartcontrol.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean("securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 別コンテナで動いているため、こっちのsecurityは削除予定
        http.authorizeHttpRequests(customizer -> customizer
            .requestMatchers("**").permitAll() 
        );
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

}
