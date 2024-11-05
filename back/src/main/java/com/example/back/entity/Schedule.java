package com.example.back.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "address_id")
    private Address address;

    private int time;

    public void setMember(Member member){
        this.member=member;
        member.getSchedules().add(this);
    }

    public void setAddress(Address address){
        this.address=address;
    }

    public static Schedule createSchedule(Member member, Address address, int time){
        Schedule schedule=new Schedule();
        schedule.setMember(member);
        schedule.setAddress(address);
        schedule.time=time;
        return schedule;
    }
}
