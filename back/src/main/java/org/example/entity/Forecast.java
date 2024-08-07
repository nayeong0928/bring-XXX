package org.example.entity;

import javax.persistence.*;

@Entity
public class Forecast {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "location_id")
    private Long locationId;

    @Enumerated(EnumType.STRING)
    private WeatherCode weatherCode;

    private int time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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
