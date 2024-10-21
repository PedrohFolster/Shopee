package com.projeto.shopee.security;

public class SecurityConstants {
    public static final String[] PUBLIC_URLS = {
        "/login",
        "/register",
        "/public/**",
        "/usuarios", // Adicione esta linha se quiser que seja público
        "/validate-session", // Adiciona o endpoint de validação de sessão
        "/categorias-l",
        "/lojas",
        "/produtos",
        "/*"
    };
}