package com.bring.back.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinRequestDto {

    private String id;
    private String name;
    private String pwd;

}
