package com.example.demo.repository;


import com.example.demo.entity.Turniket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TurniketRepository extends JpaRepository<Turniket,Integer> {

//Optional<Turniket> findByCreatedByAndStatus(UUID createdBy, boolean status);




}
