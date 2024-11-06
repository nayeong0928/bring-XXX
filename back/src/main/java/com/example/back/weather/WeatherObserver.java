package com.example.back.weather;

import lombok.Getter;

@Getter
public class WeatherObserver {

    private WeatherInfo weatherInfo;
    private Long scheduleId;

    public WeatherObserver(Long scheduleId){
        this.scheduleId=scheduleId;
    }

    public void update(WeatherInfo weatherInfo){
        this.weatherInfo=weatherInfo;
    }
}
