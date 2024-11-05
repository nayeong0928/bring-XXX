package com.example.back.repository;

import com.example.back.entity.Address;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class AddressRepository {

    private final EntityManager em;

    public void save(Address address){
        em.persist(address);
    }

    public List<Address> addressAll(){
        return em.createQuery("select a from Address a", Address.class)
                .getResultList();
    }

    public Address findOne(Long id){
        return em.find(Address.class, id);
    }
}
