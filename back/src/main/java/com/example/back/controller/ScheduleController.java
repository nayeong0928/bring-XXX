package com.example.back.controller;

import com.example.back.dto.ScheduleDto;
import com.example.back.dto.TimeBlock;
import com.example.back.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedules/{id}")
    public String timeBlock(@PathVariable("id") Long id, Model model){

        Map<Integer, TimeBlock> timeBlocks = scheduleService.schedules(id);

        model.addAttribute("addScheduleDto", new ScheduleDto());
        model.addAttribute("timeblock", timeBlocks);
        model.addAttribute("memberId", id);
        return "/schedules/timeblock";
    }

    @PostMapping("/schedules/new")
    @ResponseBody
    public ResponseEntity<?> addSchedule(@RequestBody ScheduleDto scheduleDto){
        scheduleService.addSchedule(scheduleDto.getMemberId(),
                scheduleDto.getAddressId(),
                scheduleDto.getTime());
        return ResponseEntity.ok().body(scheduleDto);
    }
}
