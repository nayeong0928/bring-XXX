package org.example;

import org.example.entity.*;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bring-db");
        EntityManager em=emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 회원 추가
            Member member=new Member();
            member.setName("홍길동");
            em.persist(member);

            // 소지품 추가
            Bring bring=new Bring();
            bring.setName("우산");
            bring.setWeatherCode(WeatherCode.RAIN);
            em.persist(bring);

            // 위치 추가
            Location location=new Location();
            location.setName("강남역");
            em.persist(location);

            // 스케줄 추가
            Schedule schedule=new Schedule();
            schedule.setTime(3);
            Member findM=em.find(Member.class, member.getId());
            schedule.setMemberId(findM.getId());
            Bring findB=em.find(Bring.class, bring.getId());
            schedule.setBringId(findB.getId());
            Location findL=em.find(Location.class, location.getId());
            schedule.setLocationId(findL.getId());
            em.persist(schedule);

            // 기상예보 추가
            Forecast forecast=new Forecast();
            forecast.setTime(3);
            forecast.setWeatherCode(WeatherCode.RAIN);
            Location foreLoc=em.find(Location.class, location.getId());
            forecast.setLocationId(foreLoc.getId());
            em.persist(forecast);

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally{
            em.close();
        }

        emf.close();
    }
}