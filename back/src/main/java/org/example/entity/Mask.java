package org.example.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MASK")
public class Mask extends Bring{

    private int value;

    public Mask(){
        this.setName("마스크");
        this.setWeatherCode(WeatherCode.DUST);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
