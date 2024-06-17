package com.bring.back.member.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationProviderTest {

    JwtAuthenticationProvider provider;

    @BeforeEach
    public void setup(){
        provider=new JwtAuthenticationProvider();
    }

    @Test
    void authenticate() {

        // create token
        String token=Jwts.builder().setClaims(Jwts.claims().setSubject("유관순")).signWith(SignatureAlgorithm.HS256, "secretkey").compact();
        JwtAuthenticationToken jwtToken=new JwtAuthenticationToken(token);
        Authentication auth=provider.authenticate(jwtToken);

        Assertions.assertThat(auth.getPrincipal()).isEqualTo("유관순");
        Assertions.assertThat(auth.getCredentials()).isEqualTo("");

    }

    @Test
    void supportsFalse() {
        Assertions.assertThat(provider.supports(Authentication.class)).isFalse();
        Assertions.assertThat(provider.supports(AbstractAuthenticationToken.class)).isFalse();
    }

    @Test
    void supportsTrue(){
        Assertions.assertThat(provider.supports(JwtAuthenticationToken.class)).isTrue();
    }
}