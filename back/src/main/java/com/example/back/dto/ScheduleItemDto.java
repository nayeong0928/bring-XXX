package com.example.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleItemDto implements Comparable<ScheduleItemDto> {

    private int time;
    private String items;

    @Override
    public int compareTo(ScheduleItemDto o) {
        return this.time-o.time;
    }
}
