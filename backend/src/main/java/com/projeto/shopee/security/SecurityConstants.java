package com.projeto.shopee.security;

public class SecurityConstants {
    public static final String[] PUBLIC_URLS = {
        "/login",
        "/register",
        "/public/**",
        "/usuarios" // Adicione esta linha se quiser que seja público
    };
}