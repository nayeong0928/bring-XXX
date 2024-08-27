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