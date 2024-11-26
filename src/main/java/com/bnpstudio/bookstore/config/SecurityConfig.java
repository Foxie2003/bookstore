package com.bnpstudio.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bnpstudio.bookstore.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Swagger UI
                .requestMatchers("/v3/api-docs/**",
                               "/swagger-ui/**",
                               "/swagger-ui.html",
                               "/webjars/**").permitAll()
                
                // API công khai 
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/otp/**").permitAll()
                // Cho phép tất cả các request khác
                // .anyRequest().permitAll()
                .requestMatchers("/linhVuc/**").permitAll()
                .requestMatchers("/danhMuc/**").authenticated()
                .requestMatchers("/donHang/**").permitAll()
                .requestMatchers("/dia-chi/**").permitAll()
                .requestMatchers("/sach/**").authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable());
        return http.build();
    }
}