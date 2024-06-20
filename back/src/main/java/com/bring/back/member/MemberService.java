package com.bring.back.member;

import com.bring.back.member.dto.LoginRequestDto;
import com.bring.back.member.dto.MemberJoinRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository repo;

}
