package com.bring.back.member.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final byte[] secretKeyByte;

    public JwtAuthenticationProvider(@Value("${jwt.valid.key}") String secretKey) {
        System.out.println("secretKey = " + secretKey);
        this.secretKeyByte = secretKey.getBytes();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Claims claims;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKeyByte)  // Use Keys.hmacShaKeyFor for key conversion
                    .build()
                    .parseClaimsJws(((JwtAuthenticationToken) authentication).getToken())
                    .getBody();
        } catch (Exception e){
            throw new AuthenticationException(e.getMessage()) {
                @Override
                public String getMessage() {
                    return e.getMessage();
                }
            };
        }

        return new JwtAuthenticationToken(claims.getSubject(), "", null);
    }

    @Override
    public boolean supports(Class<?> authentication) {

        if(JwtAuthenticationToken.class.isAssignableFrom(authentication)){
            return true;
        }

        return false;
    }
}
