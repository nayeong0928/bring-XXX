package org.example.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("UMBRELLA")
public class Umbrella extends Bring{

    private int percent;

    public Umbrella() {
        this.setName("우산");
        this.setWeatherCode(WeatherCode.RAIN);
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
