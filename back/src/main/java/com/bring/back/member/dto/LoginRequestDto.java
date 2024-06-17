package com.bring.back.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequestDto {

    private String id;
    private String pwd;

}
