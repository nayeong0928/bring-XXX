package com.example.back.weather;

import com.example.back.entity.Location;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class WeatherStation {

    private int time;
    private WeatherInfo weatherInfo;
    private HashMap<Long, WeatherObserver> observers; // scheduleId-스케줄 구독자 쌍

    public WeatherStation(int time){
        this.time=time;
        this.weatherInfo=new WeatherInfo();
        observers=new HashMap<>();
    }

    public void addObserver(Long scheduleId){
        observers.put(scheduleId, new WeatherObserver());
    }

    public void deleteObserver(Long scheduleId){
        observers.remove(scheduleId);
    }

    public WeatherObserver getWeatherObserver(Long scheduleId){
        return observers.get(scheduleId);
    }

    public void update(String category, String value){
        this.weatherInfo.setWeatherInfo(category, value);

        for(Long key: observers.keySet()){
            observers.get(key).update(weatherInfo);
        }
    }
}
