package com.example.back.controller;

import com.example.back.dto.ScheduleDto;
import com.example.back.dto.ScheduleItemDto;
import com.example.back.dto.TimeBlock;
import com.example.back.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/items/{id}")
    public String scheduleItems(@PathVariable("id") Long id, Model model){

        List<ScheduleItemDto> scheduleItemDto = scheduleService.itemsBySchedule(id);
        model.addAttribute("itemSchedule", scheduleItemDto);

        return "/schedules/schedule-items";
    }

    @PostMapping("/schedules/new")
    @ResponseBody
    public ResponseEntity<?> addSchedule(@RequestBody ScheduleDto scheduleDto){
        scheduleService.addSchedule(scheduleDto.getMemberId(),
                scheduleDto.getAddressId(),
                scheduleDto.getTime());
        return ResponseEntity.ok().body(scheduleDto);
    }

    @GetMapping("/items")
    public String updateItems() throws IOException {
        scheduleService.notice();
        return "redirect:/";
    }
}
