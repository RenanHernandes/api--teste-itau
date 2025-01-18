package com.teste.itau.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class JwtAspect {

    private final HttpServletRequest httpServletRequest;
    private final JwtUtil jwtUtil;

    public JwtAspect(HttpServletRequest httpServletRequest, JwtUtil jwtUtil) {
        this.httpServletRequest = httpServletRequest;
        this.jwtUtil = jwtUtil;
    }

    @Before("@annotation(JwtRequired)")
    public void validateJwt() {
        String token = extractJwtFromRequest(httpServletRequest);

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token JWT inválido ou ausente");
        }

        String username = jwtUtil.getUsernameFromToken(token);
        System.out.println("Usuário autenticado: " + username);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
