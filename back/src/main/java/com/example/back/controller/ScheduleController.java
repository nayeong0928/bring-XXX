package com.example.back.controller;

import com.example.back.dto.ScheduleDto;
import com.example.back.dto.TimeBlock;
import com.example.back.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public String addSchedule(ScheduleDto scheduleDto){
        scheduleService.addSchedule(scheduleDto.getMemberId(),
//                scheduleDto.getAddressId()
                2L,
                scheduleDto.getTime());
        return "redirect:/";
    }
}
