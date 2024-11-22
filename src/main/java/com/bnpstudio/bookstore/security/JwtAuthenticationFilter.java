package com.bnpstudio.bookstore.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import com.bnpstudio.bookstore.entity.UserEntity;
import com.bnpstudio.bookstore.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if(jwt == null) {
                filterChain.doFilter(request, response);
                return;
            }

            if (!jwtUtils.validateJwtToken(jwt)) {
                throw new RuntimeException("Token không hợp lệ");
            }

            String email;
            try {
                email = jwtUtils.getEmailFromJwtToken(jwt);
            } catch (Exception e) {
                throw new RuntimeException("Không thể lấy email từ token", e);
            }

            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                throw new RuntimeException("Không tìm thấy người dùng với email: " + email);
            }

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (user.isAdmin()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            try {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        email, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi thiết lập authentication", e);
            }

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xác thực người dùng", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
} 