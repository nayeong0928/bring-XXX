package com.bring.back.member.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtWebTokenIssuer {

    private final int ONE_SECOND=1000;
    private final int ONE_MINUTE=60*ONE_SECOND;
    private final SecretKey secretKey;
    private final SecretKey refreshKey;
    private final int expireMin;
    private final int refreshExpireMin;

    public JwtWebTokenIssuer(
            @Value("${jwt.valid.key}") String secretKey,
            @Value("${jwt.refresh.key") String refreshKey,
            @Value("${jwt.expire.min:10") int expireMin,
            @Value("${jwt.refresh.expire.min:30") int refreshExpireMin
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.refreshKey = Keys.hmacShaKeyFor(refreshKey.getBytes());
        this.expireMin = expireMin;
        this.refreshExpireMin = refreshExpireMin;
    }

    private String createToken(String name, SecretKey key, int expireMin){
        Date now=new Date();
        Claims claims= Jwts.claims().setSubject(name);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ONE_MINUTE*expireMin))
                .signWith(key)
                .compact();
    }

    public String createAccessToken(String name){
        return createToken(name, secretKey, expireMin);
    }

    public String createRefreshToken(String name){
        return createToken(name, refreshKey, refreshExpireMin);
    }

    public Claims parseClaimsFromRefreshToken(String jsonWebToken){
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(refreshKey)
                    .build()
                    .parseClaimsJws(jsonWebToken)
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
        return claims;
    }
}
