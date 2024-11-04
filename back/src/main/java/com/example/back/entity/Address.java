package com.example.back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Address {

    @Id @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    private String code;
    private String addr1;
    private String addr2;
    private String addr3;

    @Embedded
    private Location location;
}
