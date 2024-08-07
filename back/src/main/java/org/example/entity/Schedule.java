package org.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Schedule {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "bring_id")
    private Long bringId;

    @Column(name = "location_id")
    private Long locationId;

    private int time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getBringId() {
        return bringId;
    }

    public void setBringId(Long bringId) {
        this.bringId = bringId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
