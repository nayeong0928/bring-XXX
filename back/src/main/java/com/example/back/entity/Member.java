package com.example.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String pwd;

    protected Member(){}

    public Member(String name, String pwd) {
        this.name=name;
        this.pwd=pwd;
    }

}
