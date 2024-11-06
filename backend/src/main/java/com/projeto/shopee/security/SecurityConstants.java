package com.projeto.shopee.security;

public class SecurityConstants {
    public static final String[] PUBLIC_URLS = {
        "/login",
        "/register",
        "/public/**",
        "/usuarios", 
        "/validate-session", 
        "/categorias-l",
        "/lojas",
        "/produtos",
        "/verificar-loja",
        "/lojas/verificar-loja",
        "/lojas/CreateLoja",
        "/lojas/MinhaLoja",
        "/logout",
        "/lojas/{id}",
        "/categorias-p",
        "/status",
        "/produtos/loja/{id}",
        "/produtos",
        "/produtos/1",
        "/produtos/ativos",
        "/produtos/finalizar-compra"
    };
}