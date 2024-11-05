package com.example.back.repository;

import com.example.back.entity.Schedule;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final EntityManager em;

    public Long save(Schedule schedule){
        em.persist(schedule);
        return schedule.getId();
    }

    public Schedule findOne(Long id){
        return em.createQuery("select s from Schedule s where s.id=:id", Schedule.class)
                .getSingleResult();
    }

}
