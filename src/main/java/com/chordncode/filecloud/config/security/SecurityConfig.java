package com.chordncode.filecloud.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
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
                   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authorizeHttpRequests(request -> request.antMatchers("/signup", "/signin", "/id").permitAll()
                                                            .anyRequest().hasRole("MEMBER"))
                   .exceptionHandling(handling -> handling.authenticationEntryPoint(new AuthenticationEntryPoint() {
                                                                @Override
                                                                public void commence(HttpServletRequest request,
                                                                        HttpServletResponse response,
                                                                        AuthenticationException authException)
                                                                        throws IOException, ServletException {
                                                                            response.setStatus(401);
                                                                            response.setContentType("text/html; charset=utf-8");
                                                                            response.getWriter().print("로그인이 필요한 서비스입니다.");
                                                                }
                                                            }))
                   .exceptionHandling(handling -> handling.accessDeniedHandler(new AccessDeniedHandler() {
                                                                @Override
                                                                public void handle(HttpServletRequest request,
                                                                        HttpServletResponse response,
                                                                        AccessDeniedException accessDeniedException)
                                                                        throws IOException, ServletException {
                                                                            response.setStatus(403);
                                                                            response.setContentType("text/html; charset=utf-8");
                                                                            response.getWriter().print("접근이 거부되었습니다.");
                                                                }
                                                            }))
                   .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                   .build();
    }
    
}
