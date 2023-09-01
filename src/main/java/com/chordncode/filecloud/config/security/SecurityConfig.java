package com.chordncode.filecloud.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chordncode.filecloud.config.jwt.JwtAuthenticationFilter;
import com.chordncode.filecloud.config.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.httpBasic(basic -> basic.disable())
                   .csrf(csrf -> csrf.disable())
                   .authorizeHttpRequests(request -> request.anyRequest().permitAll())
                   .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                   .build();
    }
    
}
