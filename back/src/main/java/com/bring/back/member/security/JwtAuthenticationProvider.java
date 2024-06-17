package com.bring.back.member.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
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

    public JwtAuthenticationProvider() {
        this.secretKeyByte = "secretkey".getBytes();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

//        String id=authentication.getName();
//        String pwd=(String) authentication.getCredentials();
//        PrincipalDetails mem = (PrincipalDetails) service.loadUserByUsername(id);
//
//        if(mem.match(pwd)){
//            return authentication;
//        }

        Claims claims;

        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKeyByte).build();
        claims = jwtParser.parseClaimsJwt(((JwtAuthenticationToken) authentication).getToken()).getBody();

        return new JwtAuthenticationToken(claims.getSubject(), "", null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean result = JwtAuthenticationToken.class.isAssignableFrom(authentication);
        return result;
    }
}
