package com.bring.back.member.security;

import com.bring.back.member.dto.LoginRequestDto;
import com.bring.back.member.dto.MemberJoinRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    private final String AUTHORIZATION_HEADER="Authorization";

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberJoinRequestDto dto){

        return ResponseEntity.ok("");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto){
        JwtDto jwt = authService.login(dto);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/login")
    public ResponseEntity<?> reissue(@RequestHeader(AUTHORIZATION_HEADER)String token){
        JwtDto jwt = authService.reissue(token);
        return ResponseEntity.ok(jwt);
    }
}
