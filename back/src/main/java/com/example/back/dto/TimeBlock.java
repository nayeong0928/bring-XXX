package com.example.back.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeBlock {

    private int time;
    private Long memberId;
    private Long scheduleId;
    private Long addressId;
    private String addressStr;
}
