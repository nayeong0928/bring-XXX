package com.bring.back.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("사용자 추가")
    public void addMember(){

        memberRepository.save(new Member("5", "유관순", "1234"));
        Member member=memberRepository.findMemberById("5");

        Assertions.assertThat(member).isNotNull();

    }

    @Test
    @DisplayName("사용자 삭제")
    public void deleteMember(){

        Member member=memberRepository.findMemberById("1");
        memberRepository.delete(member);

        Assertions.assertThat(memberRepository.findMemberById("1")).isNull();
    }

    @Test
    @DisplayName("사용자 조회")
    public void allMember(){

        List<Member> members=memberRepository.findAll();

        for(Member member: members){
            System.out.println("member = " + member);
        }
    }

}