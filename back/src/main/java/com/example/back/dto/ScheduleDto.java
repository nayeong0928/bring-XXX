package com.example.back.dto;

import com.example.back.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScheduleDto {

    private Long memberId;
    private Long addressId;
    private String address;
    private int time;

}
