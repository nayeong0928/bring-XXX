package com.example.back.weather;

import com.example.back.items.ItemListGenerator;
import lombok.Getter;

import java.util.List;

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

    /**
     * 해당 날씨에 필요한 소지품 리스트를 리턴한다
     * @return
     */
    public List<String> items(){
        ItemListGenerator listGenerator=new ItemListGenerator();
        return listGenerator.getPackingList(weatherInfo);
    }
}
