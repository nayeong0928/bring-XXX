package com.bring.back.member.security;

import com.bring.back.member.Member;
import com.bring.back.member.MemberRepository;
import com.bring.back.member.dto.LoginRequestDto;
import com.bring.back.member.dto.MemberJoinRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private final AuthRepository authRepository;

    public void join(MemberJoinRequestDto dto){

    }

    public void login(LoginRequestDto dto){

    }
}
