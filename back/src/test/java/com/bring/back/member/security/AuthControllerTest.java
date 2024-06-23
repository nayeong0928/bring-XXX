package com.bring.back.member.security;

import com.bring.back.member.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private final int ONE_SECOND=1000;
    private final int ONE_MINUTE=60*ONE_SECOND;

    private SecretKey secretKey;
    private SecretKey refreshKey;
    private int expireMin;
    private int refreshExpireMin;


    @Autowired
    MockMvc mvc;

    @SpyBean
    JwtWebTokenIssuer spyJwtWebTokenIssuer;

    @BeforeEach
    void setup(){
        secretKey= Keys.hmacShaKeyFor("secretKey-for-authcontrollertest-in-bringXXX".getBytes());
        refreshKey=Keys.hmacShaKeyFor("refreshKey-for-authcontrollertest-in-bringXXX".getBytes());
        expireMin=10;
        refreshExpireMin=30;
    }

    @AfterEach
    void clear(){
        Mockito.reset(spyJwtWebTokenIssuer);
    }

    @Test
    @DisplayName("로그인 테스트")
    public void login() throws Exception {
        ObjectMapper mapper=new ObjectMapper();
        String accessToken=createAccessToken("1");
        String refreshToken=createRefreshToken("1");

        Mockito.when(spyJwtWebTokenIssuer.createAccessToken("1")).thenReturn(accessToken);
        Mockito.when(spyJwtWebTokenIssuer.createRefreshToken("1")).thenReturn(refreshToken);

        LoginRequestDto dto=new LoginRequestDto("1", "1234");

        MvcResult mvcResult=mvc.perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andReturn();

        JwtDto jwtDto=mapper.readValue(mvcResult.getResponse().getContentAsString(), JwtDto.class);
        Assertions.assertThat(jwtDto.getAccessToken()).isEqualTo(accessToken);
        Assertions.assertThat(jwtDto.getRefreshToken()).isEqualTo(refreshToken);

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


}