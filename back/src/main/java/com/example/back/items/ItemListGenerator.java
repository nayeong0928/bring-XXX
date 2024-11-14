package com.example.back.items;

import com.example.back.weather.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class ItemListGenerator {

    private List<PackingStrategy> packingStrategies;

    public ItemListGenerator() {
        packingStrategies=new ArrayList<>();
        packingStrategies.add(new UmbrellaPackingStrategy());
        packingStrategies.add(new ClothesPackingStrategy());
    }

    public List<String> getPackingList(WeatherInfo weatherInfo){

        List<String> items=new ArrayList<>();

        for(PackingStrategy strategy: packingStrategies){
            strategy.addItem(items, weatherInfo);
        }

        return items;
    }
}
