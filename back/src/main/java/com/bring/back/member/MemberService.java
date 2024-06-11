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

    public String join(MemberJoinRequestDto dto){

        System.out.println("service 호출");
        System.out.println("dto.getName() = " + dto.getName());

        Member newMem=new Member(dto.getId(), dto.getName(), dto.getPwd());
        Member member=repo.save(newMem);

        StringBuilder sb=new StringBuilder();
        sb.append(member.getName());
        sb.append("님 환영합니다.");

        return sb.toString();
    }

    public String login(LoginRequestDto dto){
        Member member=repo.findMemberById(dto.getId());

        if(member.matchPwd(dto.getPwd())){

            StringBuilder sb=new StringBuilder();
            sb.append(member.getName());
            sb.append("님 환영합니다.");

            return sb.toString();
        }

        return "로그인 실패";
    }
}
