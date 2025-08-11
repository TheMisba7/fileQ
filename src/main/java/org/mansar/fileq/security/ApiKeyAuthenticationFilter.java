package org.mansar.fileq.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mansar.fileq.ApiKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final ApiKeyService apiKeyService;

    public ApiKeyAuthenticationFilter(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader("X-API-Key");

        if (apiKey == null || !apiKeyService.isValid(apiKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                    "type": "about:blank",
                    "title": "Unauthorized",
                    "status": 401,
                    "detail": "Valid API key required",
                    "instance": "%s"
                }
                """.formatted(request.getRequestURI()));
            return;
        }

        filterChain.doFilter(request, response);
    }
}
