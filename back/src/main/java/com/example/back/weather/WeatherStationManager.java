package com.example.back.weather;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Component
@Slf4j
public class WeatherStationManager {

    /**
     * weatherStations: 장소-시간별 기상관측
     *      HashMap<Integer, WeatherStation>: 해당시간-관측소
     */
    private HashMap<String, HashMap<Integer, WeatherInfo>> weatherStations=new HashMap<>();

    public void addLocation(String addrCode){
        HashMap<Integer, WeatherInfo> hashMap=new HashMap<>();

        for(int time=0; time<=24; time++){
            hashMap.put(time, new WeatherInfo());
        }

        weatherStations.put(addrCode, hashMap);
    }

    public WeatherInfo getWeatherInfo(String addrCode, int time){

        HashMap<Integer, WeatherInfo> weatherStation = weatherStations.get(addrCode);

        if(weatherStation.get(time)==null){
            weatherStation.put(time, new WeatherInfo());
        }

        return weatherStation.get(time);
    }

    /**
     * Open-Api 파싱
     * @param str   Open-Api 요청 결과
     * @return
     */
    public void parse(String code, String str){

        JSONArray weatherItems = getWeatherItems(str);

        for (int i=0;i<weatherItems.length(); i++){
            JSONObject weather = weatherItems.getJSONObject(i);
            String date=weather.getString("fcstDate");
            String timeStr=weather.getString("fcstTime").substring(0,2);

            if(!validDateTime(date, timeStr)){
                continue;
            }

            String category=weather.getString("category");
            String value=weather.getString("fcstValue");
            int time = Integer.parseInt(timeStr);

            getWeatherInfo(code, time).addWeatherInfo(category, value);
        }

    }


    private static JSONArray getWeatherItems(String str) {
        log.info("str: {}", str);
        JSONObject jsonObject=new JSONObject(str);
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject item = (JSONObject) body.get("items");
        return item.getJSONArray("item");
    }

    /**
     * 해당 날씨 데이터가 유효한 시간대이면 true, 지난 시간이나 오늘 이후면 false를 리턴한다.
     * @param date 날씨 데이터 날짜
     * @param time 날씨 데이터 시간
     * @return
     */
    private static boolean validDateTime(String date, String time){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        LocalDateTime inputTime = LocalDateTime.parse(date + time, formatter);
        LocalDateTime curr=LocalDateTime.now();

        if(inputTime.isAfter(curr) && inputTime.getDayOfMonth()==curr.getDayOfMonth()){
            return true;
        }

        return false;
    }

}
