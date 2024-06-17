package com.bring.back.member.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTH_HEADER="Authorization";
    public static final String AUTH_BREARER="Bearer ";

    private final AuthenticationManager authManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt=parseToken(request);

        if(StringUtils.hasText(jwt)){

            try {
                Authentication jwtAuth = new JwtAuthenticationToken(jwt);
                Authentication auth = authManager.authenticate(jwtAuth);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (AuthenticationException authException){
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseToken(HttpServletRequest request){
        String authHeader=request.getHeader(AUTH_HEADER);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith(AUTH_BREARER)){
            return authHeader.substring(AUTH_BREARER.length());
        }
        return null;
    }
}
