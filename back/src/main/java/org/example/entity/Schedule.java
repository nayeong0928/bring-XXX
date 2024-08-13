package org.example.entity;

import javax.persistence.*;

@Entity
public class Schedule {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "bring_id")
    private Bring bring;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private int time;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Bring getBring() {
        return bring;
    }

    public void setBring(Bring bring) {
        this.bring = bring;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        location.addSchedule(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
