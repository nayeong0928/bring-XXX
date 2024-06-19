package com.bring.back.member.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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
        } catch (SignatureException se){
            throw new JwtInvalidException("signatrue key is different", se);
        } catch (ExpiredJwtException ee){
            throw new JwtInvalidException("expired token", ee);
        } catch (MalformedJwtException me){
            throw new JwtInvalidException("malformed token", me);
        } catch (IllegalArgumentException ie){
            throw new JwtInvalidException("using illegal argument like null", ie);
        } catch (UnsupportedJwtException ue){
            throw new JwtInvalidException("malformed jwt", ue);
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
