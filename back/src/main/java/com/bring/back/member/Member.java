package com.bring.back.member;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MEMBER")
@NoArgsConstructor(force = true)
public class Member {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String pwd;

    public Member(String id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

}
