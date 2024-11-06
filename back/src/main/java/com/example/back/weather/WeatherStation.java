package com.example.back.weather;

import com.example.back.entity.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherStation {

    private WeatherInfo weatherInfo;
    private Location location;
    private HashMap<Integer, List<WeatherObserver>> observers=new HashMap<>();

    public WeatherStation(Location location) {
        this.location = location;

        for(int time=0; time<24; time++){
            observers.put(time, new ArrayList<>());
        }
    }

    public void addObserver(int time, Long scheduleId){
        WeatherObserver observer=new WeatherObserver(scheduleId);
        observers.get(time).add(observer);
    }

    public void removeObserver(int time, Long scheduleId){

        for(WeatherObserver ob : observers.get(time)){
            if(ob.getScheduleId().equals(scheduleId)){
                observers.get(time).remove(ob);
                return;
            }
        }
    }

    public void update(WeatherInfo weatherInfo, int time){
        this.weatherInfo=weatherInfo;
        notifyObserver(time);
    }

    public void notifyObserver(int time){
        for(WeatherObserver ob : observers.get(time)){
            ob.update(weatherInfo);
        }
    }
}
