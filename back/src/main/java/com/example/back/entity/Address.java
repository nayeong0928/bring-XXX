package com.example.back.entity;

import com.example.back.weather.WeatherStation;
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

    @Transient
    private WeatherStation weatherStation;

    protected Address(){}

    public Address(String code, String addr1, String addr2, String addr3, String nx, String ny) {
        this.code = code;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.addr3 = addr3;
        this.location=new Location(nx, ny);
        weatherStation = new WeatherStation(location); // 각 장소에 대하여 API Subject 등록
    }

    @Override
    public String toString() {
        return addr1+" "+addr2+" "+addr3;
    }
}
