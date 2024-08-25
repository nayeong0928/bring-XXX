package org.example.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("PARASOL")
public class Parasol extends Bring{

    private int percent;

    public Parasol() {
        this.setName("양산");
        this.setWeatherCode(WeatherCode.SUNNY);
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
