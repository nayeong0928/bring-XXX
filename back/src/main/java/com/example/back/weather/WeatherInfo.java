package com.example.back.weather;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class WeatherInfo {

    private int temperature;
    private SkyStatus skyStatus;
    private WaterStatus waterStatus;

    public void setWeatherInfo(String category, String value){

        if(!validInfo(category)){
            return;
        }

        switch(category){
            case "TMP": // 온도
                temperature=Integer.parseInt(value);
                break;
            case "SKY":
                if("1".equals(value)){
                    skyStatus=SkyStatus.SUNNY;
                } else if("3".equals(value)){
                    skyStatus=SkyStatus.CLOUDY;
                }
                else if("4".equals(value)){
                    skyStatus=SkyStatus.DARK;
                }
                break;
            case "PTY":
                if("0".equals(value)){
                    waterStatus=WaterStatus.NONE;
                }
                else if("1".equals(value) || "2".equals(value)){ // 비, 눈비
                    waterStatus= WaterStatus.RAIN;
                }
                else if("3".equals(value)){ // 눈
                    waterStatus=WaterStatus.SNOW;
                }
                else if("4".equals(value)){ // 소나기
                    waterStatus=WaterStatus.SHOWER;
                }
                break;
        }
    }

    public boolean validInfo(String category){
        String[] categories={ "TMP", "SKY", "PTY"};

        for(String ca : categories){
            if(ca.equals(ca)) return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "temperature=" + temperature +
                ", skyStatus=" + skyStatus +
                ", waterStatus=" + waterStatus +
                '}';
    }
}
