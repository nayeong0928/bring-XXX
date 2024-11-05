package com.example.back.service;

import com.example.back.dto.ScheduleDto;
import com.example.back.dto.TimeBlock;
import com.example.back.entity.Address;
import com.example.back.entity.Member;
import com.example.back.entity.Schedule;
import com.example.back.repository.AddressRepository;
import com.example.back.repository.MemberRepository;
import com.example.back.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public void addSchedule(Long memberId, Long addrId, int time){
        Member member = memberRepository.findOne(memberId);
        Address addr = addressRepository.findOne(2L);
        Schedule schedule = Schedule.createSchedule(member, addr, time);
        scheduleRepository.save(schedule);
    }

    public Map<Integer, TimeBlock> schedules(Long memberId){
        Member member = memberRepository.findOne(memberId);
        HashMap<Integer, ScheduleDto> map=new HashMap<>();
        Map<Integer, TimeBlock> timeBlocks=new HashMap<>();

        for(Schedule schedule: member.getSchedules()){
            TimeBlock block=new TimeBlock();
            block.setMemberId(memberId);
            block.setTime(schedule.getTime());
            block.setScheduleId(schedule.getId());
            block.setAddressId(schedule.getAddress().getId());
            block.setAddressStr(schedule.getAddress().toString());
            timeBlocks.put(schedule.getTime(), block);
        }

        return timeBlocks;
    }
}
