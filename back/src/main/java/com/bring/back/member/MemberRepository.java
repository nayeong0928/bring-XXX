package com.bring.back.member;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Member findMemberById(String id);
    Member findMemberByIdAndPwd(String id, String pwd);
    List<Member> findAll();

}
