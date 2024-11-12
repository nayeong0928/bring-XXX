package com.example.back.items;

import com.example.back.weather.WaterStatus;
import com.example.back.weather.WeatherInfo;

import java.util.List;

public class UmbrellaPackingStrategy implements PackingStrategy{
    @Override
    public void addItem(List<String> items, WeatherInfo weatherInfo) {
        if(WaterStatus.RAIN.equals(weatherInfo.getWaterStatus())){
            items.add("우산");
        }
        else if(WaterStatus.SHOWER.equals(weatherInfo.getWaterStatus())){
            items.add("휴대용 우산");
        }
    }
}
