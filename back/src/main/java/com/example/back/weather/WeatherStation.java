package com.example.back.weather;

import com.example.back.entity.Location;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherStation {

    private HashMap<Integer, WeatherInfo> weatherApi=new HashMap<>();
    private Location location;
    private HashMap<Integer, List<WeatherObserver>> observers=new HashMap<>();

    public WeatherStation(Location location) {
        this.location=location;

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

    public void update(HashMap<Integer, WeatherInfo> api){

        for(int time: api.keySet()){
            this.weatherApi.put(time, api.get(time));
            notifyObserver(time);
        }

    }

    public void notifyObserver(int time){
        for(WeatherObserver ob : observers.get(time)){
            ob.update(weatherApi.get(time));
        }
    }

    public List<String> itemList(Long scheduleId){
        for(int time: weatherApi.keySet()){
            for(WeatherObserver ob : observers.get(time)){
                if(ob.getScheduleId().equals(scheduleId)){
                    List<String> items = ob.items();
                    return items;
                }
            }
        }

        return null;
    }
}
