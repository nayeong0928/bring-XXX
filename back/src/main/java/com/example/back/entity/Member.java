package com.example.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String pwd;

    @OneToMany(mappedBy = "member")
    private List<Schedule> schedules=new ArrayList<>();

    protected Member(){}

    public Member(String name, String pwd) {
        this.name=name;
        this.pwd=pwd;
    }

}
