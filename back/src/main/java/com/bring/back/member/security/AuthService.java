package com.bring.back.member.security;

import com.bring.back.member.Member;
import com.bring.back.member.dto.LoginRequestDto;
import com.bring.back.member.dto.MemberJoinRequestDto;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private final AuthRepository authRepository;
    @Autowired
    private final JwtWebTokenIssuer jwtIssuer;
    private final String GRANT_TYPE_BEARER="Bearer ";

    public String resolveToken(String bearerToken){
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE_BEARER)){
            return bearerToken.substring(7);
        }
        return null;
    }

    public JwtDto login(LoginRequestDto dto){
        Member member=authRepository.findById(dto.getId())
                .orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 아이디입니다."));

        if(!member.matchPwd(dto.getPwd())){
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }

        String accessToken= jwtIssuer.createAccessToken(dto.getId());
        String refreshToken= jwtIssuer.createRefreshToken(dto.getId());

        return new JwtDto(accessToken, refreshToken);
    }

    public JwtDto reissue(String bearerToken){
        String refreshToken=resolveToken(bearerToken);

        if(!StringUtils.hasText(refreshToken)){
            throw new JwtInvalidException("invalid grant type");
        }

        Claims claims=jwtIssuer.parseClaimsFromRefreshToken(refreshToken);

        if(claims==null){
            throw new JwtInvalidException("invalid token");
        }

        Member member=authRepository.findById(claims.getSubject())
                .orElseThrow(()->new UsernameNotFoundException("member not found"));

        String accessToken= jwtIssuer.createAccessToken(claims.getSubject());
        String newRefreshToken= jwtIssuer.createRefreshToken(claims.getSubject());

        return new JwtDto(accessToken, newRefreshToken);
    }
}
