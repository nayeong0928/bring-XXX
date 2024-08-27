package org.example.entity;

import javax.persistence.*;

@Entity
public class Forecast {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private WeatherCode weatherCode;

    private int time;

    @Embedded
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeatherCode getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(WeatherCode weatherCode) {
        this.weatherCode = weatherCode;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
