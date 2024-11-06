package com.example.back.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Location {

    private String nx;
    private String ny;

    public Location(String nx, String ny) {
        this.nx=nx;
        this.ny=ny;
    }

    public Location() {

    }
}
