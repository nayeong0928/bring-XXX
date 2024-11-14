package com.example.back.weather;

import com.example.back.items.ItemListGenerator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WeatherObserver {

    private List<String> items; // 필요한 소지품

    public void update(WeatherInfo weatherInfo){
        ItemListGenerator listGenerator=new ItemListGenerator();
        items=listGenerator.getPackingList(weatherInfo);
    }

    public String getItemList(){

        if(items==null){
            return "";
        }

        StringBuilder sb=new StringBuilder();
        for(String item: items){
            sb.append(item);
            sb.append("\n");
        }
        return sb.toString();
    }
}
