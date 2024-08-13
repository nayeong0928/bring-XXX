package org.example.entity;

import javax.persistence.*;

@Entity
public class Forecast {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Enumerated(EnumType.STRING)
    private WeatherCode weatherCode;

    private int time;

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
