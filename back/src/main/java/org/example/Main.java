package org.example;

import org.example.entity.*;

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

            Member member2=new Member();
            member2.setName("임꺽정");
            em.persist(member2);

            // 소지품 추가
            Bring bring=new Umbrella();
            em.persist(bring);

            // 장소 추가
            Location location=new Location();
            location.setName("강남구");
            em.persist(location);

            // 일정 추가
            Schedule schedule=new Schedule();
            schedule.setMember(member);
            schedule.setLocation(location);
            schedule.setBring(bring);
            schedule.setTime(12);
            em.persist(schedule);

            // 일정 추가
            Schedule schedule2=new Schedule();
            schedule2.setMember(member2);
            schedule2.setLocation(location);
            schedule2.setBring(bring);
            schedule2.setTime(12);
            em.persist(schedule2);

            // 예보 추가
            Forecast forecast=new Forecast();
            forecast.setTime(12);
            forecast.setWeatherCode(WeatherCode.RAIN);
            forecast.setLocation(location);
            em.persist(forecast);

            // 예보 추가된 지역에 일정 등록한 사람들 알아내기
            System.out.println("강남구 12시 예보 추가");
            forecast.getLocation().getSchedules().forEach(
                    s->{
                        System.out.println(s.getMember().getName());
                    }
            );

            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        } finally{
            em.close();
        }

        emf.close();
    }
}