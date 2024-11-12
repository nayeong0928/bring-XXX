package com.example.back.items;

import com.example.back.weather.WeatherInfo;

import java.util.List;

public interface PackingStrategy {

    public void addItem(List<String> items, WeatherInfo weatherInfo);
}
