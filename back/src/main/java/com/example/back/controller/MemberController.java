package com.example.back.controller;

import com.example.back.dto.MemberDto;
import com.example.back.entity.Member;
import com.example.back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String dashboard(Model model){
        model.addAttribute("members", memberService.findMembers());
        return "home";
    }

    @GetMapping("/members/new")
    public String memberJoinForm(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "/members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberDto memberDto){
        memberService.join(memberDto.getName(), memberDto.getPwd());
        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model){
        model.addAttribute("members", memberService.findMembers());
        return "/members/memberList";
    }
}
