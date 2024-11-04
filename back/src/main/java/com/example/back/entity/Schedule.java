package com.example.back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Schedule {

    @Id @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private Address address;
}
