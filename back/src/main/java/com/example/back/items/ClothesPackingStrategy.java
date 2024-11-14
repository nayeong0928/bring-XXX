package com.example.back.items;

import com.example.back.weather.WeatherInfo;

import java.util.List;

public class ClothesPackingStrategy implements PackingStrategy{
    @Override
    public void addItem(List<String> items, WeatherInfo weatherInfo) {

        if(weatherInfo.getTemperature()>28){
            items.add("긴소매");
            items.add("반팔");
            items.add("반바지");
            items.add("짧은 치마");
            items.add("린넨 옷");
        }
        else if(weatherInfo.getTemperature()>23){
            items.add("반팔");
            items.add("얇은 셔츠");
            items.add("반바지");
            items.add("면바지");
        }
        else if(weatherInfo.getTemperature()>20){
            items.add("블라우스");
            items.add("긴팔 티");
            items.add("면바지");
            items.add("슬랙스");
        }
        else if(weatherInfo.getTemperature()>17){
            items.add("얇은 가디건");
            items.add("니트");
            items.add("맨투맨");
            items.add("후드");
            items.add("긴바지");
        }
        else if(weatherInfo.getTemperature()>12){
            items.add("자켓");
            items.add("가디건");
            items.add("청자켓");
            items.add("니트");
            items.add("스타킹");
            items.add("청바지");
        }
        else if(weatherInfo.getTemperature()>9){
            items.add("트랜치코트");
            items.add("야상");
            items.add("점퍼");
            items.add("스타킹");
            items.add("기모바지");
        }
        else if(weatherInfo.getTemperature()>5){
            items.add("울코트");
            items.add("히트택");
            items.add("가죽옷");
            items.add("기모");
        }
        else {
            items.add("패딩");
            items.add("두꺼운 코트");
            items.add("누빔 옷");
            items.add("기모");
            items.add("목도리");
        }

    }
}
