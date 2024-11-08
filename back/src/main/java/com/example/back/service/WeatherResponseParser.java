package com.example.back.service;

import com.example.back.weather.WeatherInfo;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class WeatherResponseParser {

    private static HashMap<Integer, WeatherInfo> weatherApi=new HashMap<>();

    public static HashMap<Integer, WeatherInfo> parse(String str){

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
            updateWeatherApi(time, category, value);
        }

        return weatherApi;
    }

    private static JSONArray getWeatherItems(String str) {
        JSONObject jsonObject=new JSONObject(str);
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject item = (JSONObject) body.get("items");
        return item.getJSONArray("item");
    }

    private static void updateWeatherApi(int time, String category, String value){

        if(!WeatherInfo.validInfo(category)){
            return;
        }

        if(weatherApi.get(time)==null){
            weatherApi.put(time, new WeatherInfo());
        }

        weatherApi.get(time).setWeatherInfo(category, value);
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

    public static String print() {
        StringBuilder sb=new StringBuilder();

        for(int time=0;time<24;time++){
            sb.append(time+"시: ");
            if(weatherApi.get(time)!=null) {
                sb.append(weatherApi.get(time).toString());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
