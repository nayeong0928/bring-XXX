package com.bring.back.member;

import com.bring.back.member.dto.LoginRequestDto;
import com.bring.back.member.dto.MemberJoinRequestDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberJoinRequestDto dto){
        String result= memberService.join(dto);
        HashMap<String, String> map=new HashMap();
        map.put("msg", result);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto){
        String result= memberService.login(dto);
        HashMap<String, String> map=new HashMap();
        map.put("msg", result);
        return ResponseEntity.ok(map);
    }

}
