package com.projeto.shopee.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionFilter extends OncePerRequestFilter {

    private static final Set<String> validSessions = new HashSet<>();
    private static final Set<String> publicUrls = new HashSet<>(Arrays.asList(SecurityConstants.PUBLIC_URLS));

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    String requestUri = request.getRequestURI();
    System.out.println("Request URI: " + requestUri);

    if (publicUrls.contains(requestUri)) {
        System.out.println("URL pública, ignorando verificação de sessão.");
        filterChain.doFilter(request, response);
        return;
    }

    String sessionId = null;

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("sessionId".equals(cookie.getName())) {
                sessionId = cookie.getValue();
                break;
            }
        }
    }

    if (sessionId == null) {
        sessionId = request.getHeader("session-id");
    }

    System.out.println("Session ID: " + sessionId);

    if (sessionId == null || !isValidSession(sessionId)) {
        System.out.println("Sessão inválida ou não encontrada.");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }

    filterChain.doFilter(request, response);
}

public static boolean isValidSession(String sessionId) {
    return validSessions.contains(sessionId);
}

    public static void addValidSession(String sessionId) {
        validSessions.add(sessionId);
    }

    public static void removeValidSession(String sessionId) {
        validSessions.remove(sessionId);
    }
}