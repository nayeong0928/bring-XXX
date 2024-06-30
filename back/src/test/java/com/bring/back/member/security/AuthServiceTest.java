package com.bring.back.member.security;

import com.bring.back.member.Member;
import com.bring.back.member.dto.LoginRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    AuthRepository mockAuthRepository;
    JwtWebTokenIssuer mockJwtIssuer;

    AuthService authService;

    @BeforeEach
    public void setup(){
        this.mockAuthRepository= Mockito.mock(AuthRepository.class);
        this.mockJwtIssuer=Mockito.mock(JwtWebTokenIssuer.class);
        this.authService=new AuthService(mockAuthRepository, mockJwtIssuer);
    }

    @Test
    @DisplayName("기존에 가입한 사용자가 로그인할 경우 토큰을 리턴한다.")
    void issueTokenSuccess(){
        LoginRequestDto dto=new LoginRequestDto("hong-gil", "1234");
        Member member=new Member("hong-gil", "홍길동", "1234");
        Mockito.when(mockAuthRepository.findById("hong-gil")).thenReturn(Optional.of(member));
        Mockito.when(mockJwtIssuer.createAccessToken("hong-gil")).thenReturn("accessToken");
        Mockito.when(mockJwtIssuer.createRefreshToken("hong-gil")).thenReturn("refreshToken");

        JwtDto jwtDto = authService.login(dto);

        Assertions.assertThat(jwtDto.getAccessToken()).isEqualTo("accessToken");
        Assertions.assertThat(jwtDto.getRefreshToken()).isEqualTo("refreshToken");
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 로그인할 경우 예외를 던진다.")
    void wrongId(){
        LoginRequestDto dto=new LoginRequestDto("hong-gil", "1234");

        Assertions.assertThatThrownBy(()->authService.login(dto))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("존재하지 않는 아이디입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 비밀번호로 로그인할 경우 예외를 던진다.")
    void wrongPwd(){
        LoginRequestDto dto=new LoginRequestDto("hong-gil", "4567");
        Member member=new Member("hong-gil", "홍길동", "1234");
        Mockito.when(mockAuthRepository.findById("hong-gil")).thenReturn(Optional.of(member));

        Assertions.assertThatThrownBy(()->authService.login(dto))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("잘못된 비밀번호입니다.");
    }

    @Test
    @DisplayName("존재하는 사용자가 토큰을 재발급할 때 새로운 토큰을 리턴한다.")
    void reIssueTokenSuccess(){
        Member member=new Member("hong-gil", "홍길동", "1234");
        Claims claims= Jwts.claims().setSubject("hong-gil");

        Mockito.when(mockAuthRepository.findById("hong-gil")).thenReturn(Optional.of(member));
        Mockito.when(mockJwtIssuer.parseClaimsFromRefreshToken("refreshToken")).thenReturn(claims);
        Mockito.when(mockJwtIssuer.createAccessToken("hong-gil")).thenReturn("accessToken-new");
        Mockito.when(mockJwtIssuer.createRefreshToken("hong-gil")).thenReturn("refreshToken-new");

        JwtDto jwtDto = authService.reissue("Bearer refreshToken");

        Assertions.assertThat(jwtDto.getAccessToken()).isEqualTo("accessToken-new");
        Assertions.assertThat(jwtDto.getRefreshToken()).isEqualTo("refreshToken-new");
    }
}