package com.bring.back.member.security;

import com.bring.back.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    @Test
    @DisplayName("아이디로 회원 정보를 조회할 수 있다.")
    void select(){
        //when
        Member search=authRepository.findById("1").get();

        //then
        Assertions.assertThat(search).isNotNull();
        Assertions.assertThat(search.getId()).isEqualTo("1");
        Assertions.assertThat(search.getName()).isEqualTo("홍길동");

    }

}