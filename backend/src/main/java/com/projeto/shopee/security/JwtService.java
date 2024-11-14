package com.projeto.shopee.security;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.projeto.shopee.entities.UsuarioAutenticar;
import com.projeto.shopee.service.UsuarioAutenticarService;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UsuarioAutenticarService usuarioAutenticarService;

    @Autowired
    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, UsuarioAutenticarService usuarioAutenticarService) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.usuarioAutenticarService = usuarioAutenticarService;
    }

    public String getGenereteToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 36000L;

        String scope = authentication
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        String login = authentication.getName();

        UsuarioAutenticar usuario = usuarioAutenticarService.findByLogin(login);
        Long userId = usuario.getId();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("shopee")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(login)
                .claim("scope", scope)
                .claim("userId", userId) 
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Long getUserIdFromToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaim("userId");
    }
}