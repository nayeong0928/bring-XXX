package com.example.back.entity;

import com.example.back.weather.WeatherStation;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    @Transient
    private WeatherStation weatherStation;

    @OneToMany(mappedBy = "address")
    private List<Schedule> schedules=new ArrayList<>();

    protected Address(){}

    public Address(String code, String addr1, String addr2, String addr3, String nx, String ny) {
        this.code = code;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.addr3 = addr3;
        this.location=new Location(nx, ny);
    }

    @Override
    public String toString() {
        return addr1+" "+addr2+" "+addr3;
    }

    @PostLoad
    public void initWeatherStation() {
        weatherStation=new WeatherStation(location);

        for(Schedule schedule: schedules){
            weatherStation.addObserver(schedule.getTime(), schedule.getId());
        }
    }

}
