package org.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Address {

    @Id @GeneratedValue
    private Long id;

    private String city;
    private String street;
    private String zipcode;

    @OneToMany(mappedBy = "address")
    private List<Schedule> schedules=new ArrayList<>();

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void addSchedules(Schedule schedule) {
        this.schedules.add(schedule);
    }
}
